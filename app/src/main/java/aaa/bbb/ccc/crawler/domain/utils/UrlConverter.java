package aaa.bbb.ccc.crawler.domain.utils;

public class UrlConverter {

    public static String getRealPathOfLink(String address, String baseAddress) {
        String http = "http://";
        String https = "https://";
        String absoluteLink = "/";
        String notValid = "#";
        String empty = "";
        if (address.startsWith(notValid)) return empty;

        if (address.startsWith(http) || address.startsWith(https)) {//absolute link
            return address.startsWith(baseAddress) ? address : empty;
        } else if (address.startsWith(absoluteLink)) {
            return baseAddress + address;
        }
        return empty;
    }

    public static String trimUrlAddress(String address) {
        if (address.endsWith("/")) {
            address = address.substring(0, address.length() - 1);
        }
        address = address.trim();
        return address;
    }
}
