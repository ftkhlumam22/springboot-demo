package com.example.account_service.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDConverter {

    /**
     * Mengonversi UUID ke byte[].
     *
     * @param uuid UUID yang akan dikonversi.
     * @return Array byte yang mewakili UUID.
     */
    public static byte[] uuidToBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    /**
     * Mengonversi byte[] ke UUID.
     *
     * @param bytes Array byte yang mewakili UUID.
     * @return UUID yang dihasilkan.
     */
    public static UUID bytesToUUID(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long high = bb.getLong();
        long low = bb.getLong();
        return new UUID(high, low);
    }
}