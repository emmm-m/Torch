package com.bucai.torch.bean;

import java.util.List;

/**
 * Created by zxzhu
 * 2018/6/22.
 * enjoy it !!
 */
public class Location {

    /**
     * data : {"formatted_address":"中国重庆市江津区106省道","geometry":{"bounds":{"northeast":{"lat":29.0040115,"lng":106.0850331},"southwest":{"lat":28.9984521,"lng":106.0777756}},"location":{"lat":29.0006513,"lng":106.0818913},"location_type":"GEOMETRIC_CENTER","viewport":{"northeast":{"lat":29.0040115,"lng":106.0850331},"southwest":{"lat":28.9984521,"lng":106.0777756}}},"place_id":"ChIJw23s4Btm6zYRLItDan1KW5s","address_components":[{"long_name":"106省道","short_name":"S106","types":["route"]},{"long_name":"江津区","short_name":"江津区","types":["political","sublocality","sublocality_level_1"]},{"long_name":"重庆市","short_name":"重庆市","types":["locality","political"]},{"long_name":"重庆市","short_name":"重庆市","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}],"types":["route"]}
     * message : succeed
     * status : 201
     */

    private DataBean data;
    private String message;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * formatted_address : 中国重庆市江津区106省道
         * geometry : {"bounds":{"northeast":{"lat":29.0040115,"lng":106.0850331},"southwest":{"lat":28.9984521,"lng":106.0777756}},"location":{"lat":29.0006513,"lng":106.0818913},"location_type":"GEOMETRIC_CENTER","viewport":{"northeast":{"lat":29.0040115,"lng":106.0850331},"southwest":{"lat":28.9984521,"lng":106.0777756}}}
         * place_id : ChIJw23s4Btm6zYRLItDan1KW5s
         * address_components : [{"long_name":"106省道","short_name":"S106","types":["route"]},{"long_name":"江津区","short_name":"江津区","types":["political","sublocality","sublocality_level_1"]},{"long_name":"重庆市","short_name":"重庆市","types":["locality","political"]},{"long_name":"重庆市","short_name":"重庆市","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}]
         * types : ["route"]
         */

        private String formatted_address;
        private GeometryBean geometry;
        private String place_id;
        private List<AddressComponentsBean> address_components;
        private List<String> types;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public GeometryBean getGeometry() {
            return geometry;
        }

        public void setGeometry(GeometryBean geometry) {
            this.geometry = geometry;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public List<AddressComponentsBean> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(List<AddressComponentsBean> address_components) {
            this.address_components = address_components;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public static class GeometryBean {
            /**
             * bounds : {"northeast":{"lat":29.0040115,"lng":106.0850331},"southwest":{"lat":28.9984521,"lng":106.0777756}}
             * location : {"lat":29.0006513,"lng":106.0818913}
             * location_type : GEOMETRIC_CENTER
             * viewport : {"northeast":{"lat":29.0040115,"lng":106.0850331},"southwest":{"lat":28.9984521,"lng":106.0777756}}
             */

            private BoundsBean bounds;
            private LocationBean location;
            private String location_type;
            private ViewportBean viewport;

            public BoundsBean getBounds() {
                return bounds;
            }

            public void setBounds(BoundsBean bounds) {
                this.bounds = bounds;
            }

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public String getLocation_type() {
                return location_type;
            }

            public void setLocation_type(String location_type) {
                this.location_type = location_type;
            }

            public ViewportBean getViewport() {
                return viewport;
            }

            public void setViewport(ViewportBean viewport) {
                this.viewport = viewport;
            }

            public static class BoundsBean {
                /**
                 * northeast : {"lat":29.0040115,"lng":106.0850331}
                 * southwest : {"lat":28.9984521,"lng":106.0777756}
                 */

                private NortheastBean northeast;
                private SouthwestBean southwest;

                public NortheastBean getNortheast() {
                    return northeast;
                }

                public void setNortheast(NortheastBean northeast) {
                    this.northeast = northeast;
                }

                public SouthwestBean getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(SouthwestBean southwest) {
                    this.southwest = southwest;
                }

                public static class NortheastBean {
                    /**
                     * lat : 29.0040115
                     * lng : 106.0850331
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class SouthwestBean {
                    /**
                     * lat : 28.9984521
                     * lng : 106.0777756
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }

            public static class LocationBean {
                /**
                 * lat : 29.0006513
                 * lng : 106.0818913
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            public static class ViewportBean {
                /**
                 * northeast : {"lat":29.0040115,"lng":106.0850331}
                 * southwest : {"lat":28.9984521,"lng":106.0777756}
                 */

                private NortheastBeanX northeast;
                private SouthwestBeanX southwest;

                public NortheastBeanX getNortheast() {
                    return northeast;
                }

                public void setNortheast(NortheastBeanX northeast) {
                    this.northeast = northeast;
                }

                public SouthwestBeanX getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(SouthwestBeanX southwest) {
                    this.southwest = southwest;
                }

                public static class NortheastBeanX {
                    /**
                     * lat : 29.0040115
                     * lng : 106.0850331
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class SouthwestBeanX {
                    /**
                     * lat : 28.9984521
                     * lng : 106.0777756
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }
        }

        public static class AddressComponentsBean {
            /**
             * long_name : 106省道
             * short_name : S106
             * types : ["route"]
             */

            private String long_name;
            private String short_name;
            private List<String> types;

            public String getLong_name() {
                return long_name;
            }

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public List<String> getTypes() {
                return types;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }
        }
    }
}
