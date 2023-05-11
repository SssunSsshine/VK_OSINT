package data;

import com.vk.api.sdk.objects.users.Fields;

import java.util.Arrays;
import java.util.List;

public class DataForConnection {
    public static final String TOKEN = "vk1.a.hOUUmsYudIz1-0-nuelB93bK1vavZVhbAtWBOLOC97YOuhNG59xenKldGekoojLeqyqjEBY-fvbDMkDvD5OvRa_w9Xz-ioB_XZYdw_S1fmqP5NN6DEFkFeBgzO4ACkGsD3kIqNBeE1M_8WYx5bieli-gOj20EHfSHBjElc4TCp8xJFRDwSsDqLjxzcuuLb7Mx8XA7eiUFyebSPaEPNx24g";

    public static final String API_VERSION = "5.131";

    public static final Integer APP_ID = 51565891;

    public static final String CLIENT_SECRET = "uRvBcdvUB4GZlg2rwv8m";

    public static final String REDIRECT_URI = "https://oauth.vk.com/blank.html";

    public static final String SERV_KEY = "1708d8fa1708d8fa1708d8fa43141a0db9117081708d8fa74f3fe2005f3db148743891b";
    public static final Integer USER_ID = 211762761;

    public static final List<Fields> USER_FIELDS = Arrays.asList(Fields.COUNTRY,Fields.CITY,Fields.BDATE,Fields.STATUS,Fields.SEX,Fields.BOOKS,Fields.INTERESTS, Fields.PHOTO_200);
    public static final List<com.vk.api.sdk.objects.groups.Fields> GROUPS_FIELDS =  Arrays.asList(com.vk.api.sdk.objects.groups.Fields.DESCRIPTION, com.vk.api.sdk.objects.groups.Fields.STATUS);
}
