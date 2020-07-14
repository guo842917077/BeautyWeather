package com.crazyorange.beauty.entity;

import java.util.List;

public class WeatherEntity {

    /**
     * reason : 查询成功!
     * result : {"city":"天津","realtime":{"temperature":"26","humidity":"72","info":"多云","wid":"01","direct":"东南风","power":"1级","aqi":"75"},"future":[{"date":"2020-07-10","temperature":"24/28℃","weather":"阴","wid":{"day":"02","night":"02"},"direct":"东南风"},{"date":"2020-07-11","temperature":"22/27℃","weather":"阴转多云","wid":{"day":"02","night":"01"},"direct":"东风转东北风"},{"date":"2020-07-12","temperature":"23/28℃","weather":"阴","wid":{"day":"02","night":"02"},"direct":"东风转东北风"},{"date":"2020-07-13","temperature":"22/29℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"东北风转西风"},{"date":"2020-07-14","temperature":"23/33℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"西风转东北风"}]}
     * error_code : 0
     */

    private String reason;
    private WeatherBean result;
    private int errorCode;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public WeatherBean getResult() {
        return result;
    }

    public void setResult(WeatherBean result) {
        this.result = result;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public static class WeatherBean {
        /**
         * city : 天津
         * realtime : {"temperature":"26","humidity":"72","info":"多云","wid":"01","direct":"东南风","power":"1级","aqi":"75"}
         * future : [{"date":"2020-07-10","temperature":"24/28℃","weather":"阴","wid":{"day":"02","night":"02"},"direct":"东南风"},{"date":"2020-07-11","temperature":"22/27℃","weather":"阴转多云","wid":{"day":"02","night":"01"},"direct":"东风转东北风"},{"date":"2020-07-12","temperature":"23/28℃","weather":"阴","wid":{"day":"02","night":"02"},"direct":"东风转东北风"},{"date":"2020-07-13","temperature":"22/29℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"东北风转西风"},{"date":"2020-07-14","temperature":"23/33℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"西风转东北风"}]
         */

        private String city;
        private RealtimeBean realtime;
        private List<FutureBean> future;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public RealtimeBean getRealtime() {
            return realtime;
        }

        public void setRealtime(RealtimeBean realtime) {
            this.realtime = realtime;
        }

        public List<FutureBean> getFuture() {
            return future;
        }

        public void setFuture(List<FutureBean> future) {
            this.future = future;
        }

        public static class RealtimeBean {
            /**
             * temperature : 26
             * humidity : 72
             * info : 多云
             * wid : 01
             * direct : 东南风
             * power : 1级
             * aqi : 75
             */

            private String temperature;
            private String humidity;
            private String info;
            private String wid;
            private String direct;
            private String power;
            private String aqi;

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getWid() {
                return wid;
            }

            public void setWid(String wid) {
                this.wid = wid;
            }

            public String getDirect() {
                return direct;
            }

            public void setDirect(String direct) {
                this.direct = direct;
            }

            public String getPower() {
                return power;
            }

            public void setPower(String power) {
                this.power = power;
            }

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }
        }

        public static class FutureBean {
            /**
             * date : 2020-07-10
             * temperature : 24/28℃
             * weather : 阴
             * wid : {"day":"02","night":"02"}
             * direct : 东南风
             */

            private String date;
            private String temperature;
            private String weather;
            private WidBean wid;
            private String direct;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public WidBean getWid() {
                return wid;
            }

            public void setWid(WidBean wid) {
                this.wid = wid;
            }

            public String getDirect() {
                return direct;
            }

            public void setDirect(String direct) {
                this.direct = direct;
            }

            public static class WidBean {
                /**
                 * day : 02
                 * night : 02
                 */

                private String day;
                private String night;

                public String getDay() {
                    return day;
                }

                public void setDay(String day) {
                    this.day = day;
                }

                public String getNight() {
                    return night;
                }

                public void setNight(String night) {
                    this.night = night;
                }
            }
        }
    }
}
