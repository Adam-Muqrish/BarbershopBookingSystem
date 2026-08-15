-- Store profile pictures as bytes in the database instead of the ephemeral
-- dyno filesystem (Heroku wipes runtime files on every deploy/restart).
-- The original filename is kept in cust_picture/staff_picture so the image
-- content type can be derived from its extension when serving.
-- Created: 2026-08-15

ALTER TABLE customers ADD COLUMN IF NOT EXISTS cust_picture_data BYTEA;
ALTER TABLE staffs ADD COLUMN IF NOT EXISTS staff_picture_data BYTEA;