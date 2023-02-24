package com.booking.system.utility;

public class HexConvertor {
    public String toHexString(String text) {
        byte[] ba = text.getBytes();
        StringBuilder str = new StringBuilder();
        for (byte b : ba)
            str.append(String.format("%x", b));
        return str.toString();
    }

    public String fromHexString(String hex) {
        System.out.println(hex);
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        }
        return str.toString();
    }

    // TODO alternatively use Hex from spring -> new String(Hex.encode(email.getBytes()));
}
