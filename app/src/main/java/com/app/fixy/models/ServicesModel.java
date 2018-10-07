package com.app.fixy.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham verma on 30-09-2018.
 */

public class ServicesModel extends BaseModel {


    /**
     * response : {"categories":[{"id":"1","category_name":"Beauty","category_pic":"","description":"this is beauty","category_price":"20","total":"2","subcategories":[{"id":"5","category_name":"Body message","category_pic":"","description":"wetqertegbr","category_price":"20"},{"id":"6","category_name":"Waxing","category_pic":"","description":"Waxingn zxcmsdmfvbsdm","category_price":"10"}]},{"id":"4","category_name":"Plumber","category_pic":"","description":"Plumber","category_price":"35","total":"2","subcategories":[{"id":"7","category_name":"water pump","category_pic":"","description":"qwewqr","category_price":"25"},{"id":"8","category_name":"wash repair","category_pic":"","description":"yuryur","category_price":"35"}]}],"recommend_services":[],"offer_services":[]}
     */

    private ResponseBean response;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        private ArrayList<CategoriesBean> categories;
        private List<?> recommend_services;
        private List<?> offer_services;

        public ArrayList<CategoriesBean> getCategories() {
            return categories;
        }

        public void setCategories(ArrayList<CategoriesBean> categories) {
            this.categories = categories;
        }

        public List<?> getRecommend_services() {
            return recommend_services;
        }

        public void setRecommend_services(List<?> recommend_services) {
            this.recommend_services = recommend_services;
        }

        public List<?> getOffer_services() {
            return offer_services;
        }

        public void setOffer_services(List<?> offer_services) {
            this.offer_services = offer_services;
        }

        public static class CategoriesBean implements Parcelable {
            public static final Parcelable.Creator<CategoriesBean> CREATOR = new Parcelable.Creator<CategoriesBean>() {
                @Override
                public CategoriesBean createFromParcel(Parcel source) {
                    return new CategoriesBean(source);
                }

                @Override
                public CategoriesBean[] newArray(int size) {
                    return new CategoriesBean[size];
                }
            };
            /**
             * id : 1
             * category_name : Beauty
             * category_pic :
             * description : this is beauty
             * category_price : 20
             * total : 2
             * subcategories : [{"id":"5","category_name":"Body message","category_pic":"","description":"wetqertegbr","category_price":"20"},{"id":"6","category_name":"Waxing","category_pic":"","description":"Waxingn zxcmsdmfvbsdm","category_price":"10"}]
             */

            private String id;
            private String category_name;
            private String category_pic;
            private String description;
            private String category_price;
            private String total;
            private ArrayList<SubcategoriesBean> subcategories=new ArrayList<>();

            public CategoriesBean() {
            }

            protected CategoriesBean(Parcel in) {
                this.id = in.readString();
                this.category_name = in.readString();
                this.category_pic = in.readString();
                this.description = in.readString();
                this.category_price = in.readString();
                this.total = in.readString();
                this.subcategories = in.createTypedArrayList(SubcategoriesBean.CREATOR);
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
            }

            public String getCategory_pic() {
                return category_pic;
            }

            public void setCategory_pic(String category_pic) {
                this.category_pic = category_pic;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getCategory_price() {
                return category_price;
            }

            public void setCategory_price(String category_price) {
                this.category_price = category_price;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public ArrayList<SubcategoriesBean> getSubcategories() {
                return subcategories;
            }

            public void setSubcategories(ArrayList<SubcategoriesBean> subcategories) {
                this.subcategories = subcategories;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.category_name);
                dest.writeString(this.category_pic);
                dest.writeString(this.description);
                dest.writeString(this.category_price);
                dest.writeString(this.total);
                dest.writeTypedList(this.subcategories);
            }

            public static class SubcategoriesBean implements Parcelable {
                public static final Parcelable.Creator<SubcategoriesBean> CREATOR = new Parcelable.Creator<SubcategoriesBean>() {
                    @Override
                    public SubcategoriesBean createFromParcel(Parcel source) {
                        return new SubcategoriesBean(source);
                    }

                    @Override
                    public SubcategoriesBean[] newArray(int size) {
                        return new SubcategoriesBean[size];
                    }
                };
                /**
                 * id : 5
                 * category_name : Body message
                 * category_pic :
                 * description : wetqertegbr
                 * category_price : 20
                 */

                private String id;
                private String category_name;
                private String category_pic;
                private String description;
                private String category_price;

                public SubcategoriesBean() {
                }

                protected SubcategoriesBean(Parcel in) {
                    this.id = in.readString();
                    this.category_name = in.readString();
                    this.category_pic = in.readString();
                    this.description = in.readString();
                    this.category_price = in.readString();
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCategory_name() {
                    return category_name;
                }

                public void setCategory_name(String category_name) {
                    this.category_name = category_name;
                }

                public String getCategory_pic() {
                    return category_pic;
                }

                public void setCategory_pic(String category_pic) {
                    this.category_pic = category_pic;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getCategory_price() {
                    return category_price;
                }

                public void setCategory_price(String category_price) {
                    this.category_price = category_price;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.id);
                    dest.writeString(this.category_name);
                    dest.writeString(this.category_pic);
                    dest.writeString(this.description);
                    dest.writeString(this.category_price);
                }
            }
        }
    }
}
