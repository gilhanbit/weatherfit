package com.weatherfit.common.util;

public class GridConverter {

    public static class GridCoord {
        public int nx;  // 격자 X 좌표
        public int ny;  // 격자 Y 좌표

        public GridCoord(int nx, int ny) {
            this.nx = nx;
            this.ny = ny;
        }
    }

    public static GridCoord convertToGrid(double lat, double lon) {
        final double RE = 6371.00877; // 지구 반경(km)
        final double GRID = 5.0; // 격자 간격(km)
        final double SLAT1 = 30.0; // 표준 위도 1 (degree)
        final double SLAT2 = 60.0; // 표준 위도 2 (degree)
        final double OLON = 126.0; // 기준 경도 (degree)
        final double OLAT = 38.0; // 기준 위도 (degree)
        final double XO = 43; // 기준 X좌표
        final double YO = 136; // 기준 Y좌표

        final double DEGRAD = Math.PI / 180.0;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);

        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;

        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);

        double ra = Math.tan(Math.PI * 0.25 + lat * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);

        double theta = lon * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;

        int nx = (int) Math.floor(ra * Math.sin(theta) + XO + 0.5);
        int ny = (int) Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);

        return new GridCoord(nx, ny);
    }
}
