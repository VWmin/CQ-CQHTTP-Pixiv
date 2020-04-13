package com.vwmin.coolq.pixiv;

import lombok.Data;

import java.io.Serializable;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/13 13:11
 */
@Data
public class UserResponse implements Serializable {

    /**
     * user : {"id":123,"name":"wagasian","account":"wohao0501","profile_image_urls":{"medium":"https://i.pximg.net/user-profile/img/2008/12/04/15/00/00/406776_4c394722161dc54f7aa647122061e6c9_170.jpg"},"comment":"web programer & illustrator & DAGASHI-YA","_followed":false}
     */

    private UserBean user;


    @Data
    public static class UserBean implements Serializable{
        /**
         * id : 123
         * name : wagasian
         * account : wohao0501
         * profile_image_urls : {"medium":"https://i.pximg.net/user-profile/img/2008/12/04/15/00/00/406776_4c394722161dc54f7aa647122061e6c9_170.jpg"}
         * comment : web programer & illustrator & DAGASHI-YA
         * _followed : false
         */

        private int id;
        private String name;
        private String account;
        private ProfileImageUrlsBean profile_image_urls;
        private String comment;
        private boolean _followed;


        @Data
        public static class ProfileImageUrlsBean implements Serializable{
            /**
             * medium : https://i.pximg.net/user-profile/img/2008/12/04/15/00/00/406776_4c394722161dc54f7aa647122061e6c9_170.jpg
             */

            private String medium;


        }
    }
}

