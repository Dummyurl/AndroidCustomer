package com.samyotech.fabcustomer.interfacess;

/**
 * Created by shubham on 19/7/17.
 */

public interface Consts {
    public static String APP_NAME = "FabCustomer";

    //  public String BASE_URL = "http://phpstack-132936-444295.cloudwaysapps.com/Webservice/";
    public String BASE_URL = "http://phpstack-132936-544601.cloudwaysapps.com/Webservice/";

    /*Api Details*/
    public String GET_ALL_ARTISTS_API = "getAllArtists";
    public String GET_ARTIST_BY_ID_API = "getArtistByid";
    public String GET_NOTIFICATION_API = "getNotifications";
    public String GET_INVOICE_API = "getMyInvoice";
    public String GET_REFERRAL_CODE_API = "getMyReferralCode";
    public String GET_CHAT_HISTORY_API = "getChatHistoryForUser";
    public String GET_CHAT_API = "getChat";
    public String SEND_CHAT_API = "sendmsg";
    public String LOGIN_API = "signIn";
    public String REGISTER_API = "SignUp";
    public String UPDATE_PROFILE_API = "editPersonalInfo";
    public String CURRENT_BOOKING_API = "getMyCurrentBookingUser";
    public String BOOK_ARTIST_API = "book_artist";
    public String BOOK_APPOINTMENT_API = "book_appointment";
    public String DECLINE_BOOKING_API = "decline_booking";
    public String UPDATE_LOCATION_API = "updateLocation";
    public String MAKE_PAYMENT_API = "makePayment";
    public String CHECK_COUPON_API = "checkCoupon";
    public String GET_MY_TICKET_API = "getMyTicket";
    public String GENERATE_TICKET_API = "generateTicket";
    public String GET_TICKET_COMMENTS_API = "getTicketComments";
    public String ADD_TICKET_COMMENTS_API = "addTicketComments";
    public String FORGET_PASSWORD_API = "forgotPassword";
    public String GET_APPOINTMENT_API = "getAppointment";
    public String EDIT_APPOINTMENT_API = "edit_appointment";
    public String DECLINE_APPOINTMENT_API = "declineAppointment";

    /*app data*/
    public static String INTROAPP = "INTROAPP";
    public static String PICKER_FLAG = "picker_flag";
    String CAMERA_ACCEPTED = "camera_accepted";
    String STORAGE_ACCEPTED = "storage_accepted";
    String MODIFY_AUDIO_ACCEPTED = "modify_audio_accepted";
    String CALL_PRIVILAGE = "call_privilage";
    String FINE_LOC = "fine_loc";
    String CORAS_LOC = "coras_loc";
    String CALL_PHONE = "call_phone";
    /*app data*/

    /*Project Parameter*/
    String ARTIST_ID = "artist_id";
    String CHAT_LIST_DTO = "chat_list_dto";
    String USER_DTO = "user_dto";
    String IS_REGISTERED = "is_registered";
    String IMAGE_URI_CAMERA = "image_uri_camera";
    String DATE_FORMATE_SERVER = "EEE, MMM dd, yyyy HH:mm a"; //Wed, JUL 06, 2018 04:30 pm

    String DATE_FORMATE_TIMEZONE = "z";
    String HISTORY_DTO = "history_dto";
    String BROADCAST = "broadcast";

    /*Parameter Get Artist and Search*/
    String USER_ID = "user_id";
    String LATITUDE = "latitude";
    String LONGITUDE = "longitude";

    /*Get All History*/
    String ROLE = "role";

    /*Send Message*/
    String MESSAGE = "message";
    String SEND_BY = "send_by";
    String SENDER_NAME = "sender_name";


    /*Login Parameter*/
    String NAME = "name";
    String EMAIL_ID = "email_id";
    String PASSWORD = "password";
    String DEVICE_TYPE = "device_type";
    String DEVICE_TOKEN = "device_token";
    String DEVICE_ID = "device_id";
    String REFERRAL_CODE = "referral_code";


    /*Update Profile*/
    String NEW_PASSWORD = "new_password";
    String GENDER = "gender";
    String MOBILE = "mobile";
    String OFFICE_ADDRESS = "office_address";
    String ADDRESS = "address";
    String IMAGE = "image";

    /*Book Artist*/

    String DATE_STRING = "date_string";
    String TIMEZONE = "timezone";
    String PRICE = "price";

    /*Decline*/
    String BOOKING_ID = "booking_id";
    String DECLINE_BY = "decline_by";
    String DECLINE_REASON = "decline_reason";

    /*Make Payment*/
    String INVOICE_ID = "invoice_id";
    // String USER_ID = "user_id";
    String COUPON_CODE = "coupon_code";
    String FINAL_AMOUNT = "final_amount";
    String PAYMENT_STATUS = "payment_status";


    /*Chat intent*/
    String ARTIST_NAME = "artist_name";

    /*Add Ticket*/
    String REASON = "reason";


    /*Get Ticket*/
    String TICKET_ID = "ticket_id";
    String COMMENT = "comment";


    /*Edit Appointment*/
    String APPOINTMENT_ID = "appointment_id";
}
