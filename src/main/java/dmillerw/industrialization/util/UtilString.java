package dmillerw.industrialization.util;

import net.minecraft.client.gui.FontRenderer;

/**
 * Created by Dylan Miller on 1/4/14
 */
public class UtilString {

    public static String squish(String string, FontRenderer renderer, int length) {
        if (renderer.getStringWidth(string) >= length) {
            return string;
        }

        int index = 0;
        StringBuilder sb = new StringBuilder();
        while((renderer.getStringWidth(sb.toString()) + renderer.getStringWidth("....")) > length) {
            sb.append(string.charAt(index));
            index++;
        }

        sb.append("...");
        return sb.toString();
    }

    public static String capitalize(String string) {
        StringBuffer sb = new StringBuffer();
        char[] chars = string.toCharArray();

        for (int i=0; i<chars.length; i++) {
            char c = chars[i];

            if (i == 0 || Character.isWhitespace(chars[i - 1])) {
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /* Helper method that inserts a space before any change from lower case to upper case */
    public static String insertSpacing(String string) {
        StringBuilder sb = new StringBuilder();
        char[] chars = string.toCharArray();

        for (int i=0; i<chars.length; i++) {
            char c = chars[i];

            if (i != chars.length - 1 && (Character.isLowerCase(c) && Character.isUpperCase(chars[i + 1]))) {
                sb.append(c);
                sb.append(" ");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
