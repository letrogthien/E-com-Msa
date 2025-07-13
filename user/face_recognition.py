import os
import time
import face_recognition
import mysql.connector
import schedule
from datetime import datetime
from pathlib import Path



DB_CONFIG = {
    "host": "localhost",
    "user": "root",
    "password": "root",
    "database": "app_db"
}

def get_pending_verifications():
    conn = mysql.connector.connect(**DB_CONFIG)
    cursor = conn.cursor(dictionary=True)
    cursor.execute("""
        SELECT * FROM user_verifications
        WHERE verification_status = 'PENDING'
    """)
    rows = cursor.fetchall()
    cursor.close()
    conn.close()
    return rows
def update_verification_status(id, status, verified_at=None):
    conn = mysql.connector.connect(**DB_CONFIG)
    cursor = conn.cursor()
    cursor.execute("""
        UPDATE user_verifications
        SET verification_status = %s, verified_at = %s
        WHERE id = %s
    """, (status, verified_at, id))
    conn.commit()
    cursor.close()
    conn.close()

def verify_faces(selfie_path, id_path):
    try:
        selfie_img = face_recognition.load_image_file(selfie_path)
        id_img = face_recognition.load_image_file(id_path)

        selfie_enc = face_recognition.face_encodings(selfie_img)
        id_enc = face_recognition.face_encodings(id_img)

        if not selfie_enc or not id_enc:
            return False

        result = face_recognition.compare_faces([id_enc[0]], selfie_enc[0], tolerance=0.6)
        return result[0]
    except:
        return False


def resolve_path(relative_path):
    full_path = os.path.join(BASE_FILE_DIR, relative_path)
    return full_path if os.path.exists(full_path) else None

def process_pending_verifications():
    records = get_pending_verifications()

    for record in records:

        selfie_path = resolve_path(record.get("face_id_smile_url", ""))
        id_path = resolve_path(record.get("document_front_url", ""))

        if not selfie_path or not id_path:
            continue

        matched = verify_faces(selfie_path, id_path)
        now = datetime.now()

        if matched:
            update_verification_status(record["id"], "APPROVED", now)
        else:
            update_verification_status(record["id"], "REJECTED", now)

schedule.every(CHECK_INTERVAL_MINUTES).minutes.do(process_pending_verifications)

if __name__ == "__main__":
    while True:
        schedule.run_pending()
        time.sleep(1)