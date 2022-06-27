package hcmute.edu.vn.foody_18.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import hcmute.edu.vn.foody_18.Model.Food;
import hcmute.edu.vn.foody_18.Model.Menu;
import hcmute.edu.vn.foody_18.Model.Order;
import hcmute.edu.vn.foody_18.Model.OrderDetail;
import hcmute.edu.vn.foody_18.Model.Restaurant;
import hcmute.edu.vn.foody_18.Model.User;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler mInstance = null;
    private final Context context;

    public static String DATABASE_NAME = "foody_database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_FOODS = "foods";
    private static final String TABLE_FOODS_MENU = "foods_menu";
    private static final String TABLE_ORDERS = "orders";
    private static final String TABLE_ORDER_DETAILS = "order_details";
    private static final String TABLE_RESTAURANTS = "restaurants";
    private static final String TABLE_USERS = "users";

    private static final String KEY_ADDRESS = "address";
    private static final String KEY_COVER_IMAGE = "cover_image";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PAID = "paid";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PRICE = "price";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_SOLD = "sold";
    private static final String KEY_THUMB_IMAGE = "thumb_image";
    private static final String KEY_TITLE = "title";
    private static final String KEY_UPDATED_AT = "updated_at";
    private static final String KEY_VOTE = "vote";

    private static final String FOREIGN_KEY_REF_FOOD = "food_id";
    private static final String FOREIGN_KEY_REF_FOOD_MENU = "foods_menu_id";
    private static final String FOREIGN_KEY_REF_ORDER = "order_id";
    private static final String FOREIGN_KEY_REF_RESTAURANT = "restaurant_id";
    private static final String FOREIGN_KEY_REF_USER = "user_id";

    private static final String CREATE_TABLE_FOODS = String.format("CREATE TABLE IF NOT EXISTS %s(%s INTEGER PRIMARY KEY, %s INT, %s TEXT, %s INT, %s INT, %s TEXT, %s TEXT, %s TEXT, FOREIGN KEY(%s) REFERENCES %s(%s));", TABLE_FOODS, KEY_ID, FOREIGN_KEY_REF_FOOD_MENU, KEY_NAME, KEY_PRICE, KEY_SOLD, KEY_DESCRIPTION, KEY_COVER_IMAGE, KEY_THUMB_IMAGE, FOREIGN_KEY_REF_FOOD_MENU, TABLE_FOODS_MENU, KEY_ID);
    private static final String CREATE_TABLE_FOODS_MENU = String.format("CREATE TABLE IF NOT EXISTS %s(%s INTEGER PRIMARY KEY, %s INT, %s TEXT, FOREIGN KEY(%s) REFERENCES %s(%s));", TABLE_FOODS_MENU, KEY_ID, FOREIGN_KEY_REF_RESTAURANT, KEY_TITLE, FOREIGN_KEY_REF_RESTAURANT, TABLE_RESTAURANTS, KEY_ID);
    private static final String CREATE_TABLE_ORDERS = String.format("CREATE TABLE IF NOT EXISTS %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INT, %s INT, %s INT DEFAULT 0, %s TEXT, %s TEXT, %s TEXT, %s DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(%s) REFERENCES %s(%s), FOREIGN KEY(%s) REFERENCES %s(%s));", TABLE_ORDERS, KEY_ID, FOREIGN_KEY_REF_USER, FOREIGN_KEY_REF_RESTAURANT, KEY_PAID, KEY_NAME, KEY_PHONE, KEY_ADDRESS, KEY_CREATED_AT, FOREIGN_KEY_REF_USER, TABLE_USERS, KEY_ID, FOREIGN_KEY_REF_RESTAURANT, TABLE_RESTAURANTS, KEY_ID);
    private static final String CREATE_TABLE_ORDER_DETAILS = String.format("CREATE TABLE IF NOT EXISTS %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INT, %s INT, %s INT DEFAULT 1, %s INT, %s DATETIME DEFAULT CURRENT_TIMESTAMP, %s DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(%s) REFERENCES %s(%s), FOREIGN KEY(%s) REFERENCES %s(%s));", TABLE_ORDER_DETAILS, KEY_ID, FOREIGN_KEY_REF_ORDER, FOREIGN_KEY_REF_FOOD, KEY_QUANTITY, KEY_PRICE, KEY_CREATED_AT, KEY_UPDATED_AT, FOREIGN_KEY_REF_ORDER, TABLE_ORDERS, KEY_ID, FOREIGN_KEY_REF_FOOD, TABLE_FOODS, KEY_ID);
    private static final String CREATE_TABLE_RESTAURANTS = String.format("CREATE TABLE IF NOT EXISTS %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s INT, %s TEXT, %s TEXT);", TABLE_RESTAURANTS,KEY_ID, KEY_NAME, KEY_ADDRESS, KEY_VOTE, KEY_COVER_IMAGE, KEY_THUMB_IMAGE);
    private static final String CREATE_TABLE_USERS = String.format("CREATE TABLE IF NOT EXISTS %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT);", TABLE_USERS, KEY_ID, KEY_NAME, KEY_EMAIL, KEY_PASSWORD, KEY_ADDRESS, KEY_PHONE);

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        Log.d("table", CREATE_TABLE_RESTAURANTS);
    }

    public static synchronized DatabaseHandler getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new DatabaseHandler(ctx.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_FOODS);
        sqLiteDatabase.execSQL(CREATE_TABLE_FOODS_MENU);
        sqLiteDatabase.execSQL(CREATE_TABLE_ORDERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_ORDER_DETAILS);
        sqLiteDatabase.execSQL(CREATE_TABLE_RESTAURANTS);
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);

        //Restaurant 01
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RESTAURANTS + " VALUES(1, 'Hằng Ù - Chân Gà Rút Xương Ngâm Sả Tắc - Shop Online', '28/25 Lê Thị Hồng, Gò Vấp, TP. HCM', 100, 'https://images.foody.vn/res/g93/921963/prof/s640x400/foody-upload-api-foody-mobile-5-190531101349.jpg', 'https://images.foody.vn/res/g93/921963/prof/s640x400/foody-upload-api-foody-mobile-5-190531101349.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(1, 1, 'CHÂN GÀ CÓ XƯƠNG')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(1, 1, 'Chân Gà Có Xương Ngâm Sả Tắc - Lớn', 120000, 300, '1 hộp từ 0,6kg đến 0,7kg chân gà (không tính nước sốt,tắc,sả,ớt và gia vị)', 'https://images.foody.vn/res/g93/921963/s120x120/45e10554-c870-4f8f-8d62-ff622ef4d5cf.jpg', 'https://images.foody.vn/res/g93/921963/s120x120/45e10554-c870-4f8f-8d62-ff622ef4d5cf.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(2, 1, 'Chân gà ngâm sả tắc Bịch', 30000, 300, '200gr có xương', 'https://images.foody.vn/res/g93/921963/s120x120/f74b0108-2d61-47e7-815c-a7ceedcf-0369b32b-211020112025.jpeg', 'https://images.foody.vn/res/g93/921963/s120x120/f74b0108-2d61-47e7-815c-a7ceedcf-0369b32b-211020112025.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(3, 1, 'Chân Gà Có Xương Ngâm Sả Tắc - Nhỏ', 65000, 299, '1 hộp từ 0,3kg đến 0,4kg chân gà (không tính nước ngâm,tắc,sả,ớt và gia vị)', 'https://images.foody.vn/res/g93/921963/s120x120/397b6bcb-b251-4883-9897-6043ff3f-88460672-220121081330.jpeg', 'https://images.foody.vn/res/g93/921963/s120x120/397b6bcb-b251-4883-9897-6043ff3f-88460672-220121081330.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(4, 1, 'Chân Gà Có Xương Sốt Thái', 120000, 199, '1 hộp từ 0,6kg đến 0,7kg chân gà (không tính nước sốt,tắc,sả,ớt và gia vị)', 'https://images.foody.vn/res/g93/921963/s120x120/5ca5b504-46e5-47a9-bb55-67acd4d8-a84764a0-220121082955.jpeg', 'https://images.foody.vn/res/g93/921963/s120x120/5ca5b504-46e5-47a9-bb55-67acd4d8-a84764a0-220121082955.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(5, 1, 'Chân Gà Có Xương Lắc Cóc Xoài', 120000, 199, 'Một hộp từ 0,6kg đến 0,7kg chân gà (không tính nước sốt,tắc,sả,ớt và gia vị)', 'https://images.foody.vn/res/g93/921963/s120x120/e63bf327-29c0-4e0f-8547-dc10e647-f0ab3c4b-220121083941.jpeg', 'https://images.foody.vn/res/g93/921963/s120x120/e63bf327-29c0-4e0f-8547-dc10e647-f0ab3c4b-220121083941.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(2, 1, 'CHÂN GÀ RÚT XƯƠNG')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(6, 2, 'Chân gà rút xương ngâm sả tắc (hộp nhỏ)', 110000, 300, 'Cân đúng 0,25kg Chân Gà Rút Xương(không tính nước sốt,tắc,sả,ớt và gia vị)', 'https://images.foody.vn/res/g93/921963/s120x120/994fcdbe-a160-4fc2-b7d8-82babaf96533.jpg', 'https://images.foody.vn/res/g93/921963/s120x120/994fcdbe-a160-4fc2-b7d8-82babaf96533.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(7, 2, 'Chân gà rút xương ngâm sả tắc (hộp lớn)', 210000, 300, 'Cân đúng 0,5kg Chân Gà Rút Xương(không tính nước sốt,tắc,sả,ớt và gia vị)', 'https://images.foody.vn/res/g93/921963/s120x120/61664898-773e-43c3-84e8-d45e972b2e5b.jpg', 'https://images.foody.vn/res/g93/921963/s120x120/61664898-773e-43c3-84e8-d45e972b2e5b.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(8, 2, 'Chân Gà Rút Xương Lắc Cóc Xoài ,Hộp Nhỏ', 110000, 299, 'Cân đúng 0,25kg Chân Gà Rút Xương(không tính cóc hoặc xoài,sả,ớt và gia vị)', 'https://images.foody.vn/res/g93/921963/s120x120/db00cfa2-0d05-4950-ba48-572d51e2ebdc.jpg', 'https://images.foody.vn/res/g93/921963/s120x120/db00cfa2-0d05-4950-ba48-572d51e2ebdc.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(9, 2, 'Chân gà rút xương xào bơ tỏi( hộp nhỏ)', 110000, 199, 'Cân đúng 0,25kg Chân Gà Rút Xương(không tính nước sốt,tắc,sả,ớt và gia vị)', 'https://images.foody.vn/res/g93/921963/s120x120/c273018b-f5f7-4dba-bcde-193440da6aeb.jpg', 'https://images.foody.vn/res/g93/921963/s120x120/c273018b-f5f7-4dba-bcde-193440da6aeb.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(10, 2, 'Chân gà rút xương xào cay(hộp nhỏ)', 110000, 199, 'Cân đúng 0,25kg Chân Gà Rút Xương(không tính nước sốt,tắc,sả,ớt và gia vị)', 'https://images.foody.vn/res/g93/921963/s120x120/b4f0a296-defb-44e8-8ff3-e70e30b7514e.jpg', 'https://images.foody.vn/res/g93/921963/s120x120/b4f0a296-defb-44e8-8ff3-e70e30b7514e.jpg')");

        //Restaurant 02
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RESTAURANTS + " VALUES(2, 'The Alley - Trà Sữa Đài Loan - Nguyễn Gia Trí', '91 Nguyễn Gia Trí (Đường D2), P. 25, Bình Thạnh, TP. HCM', 100, 'https://images.foody.vn/res/g77/765621/prof/s640x400/foody-upload-api-foody-mobile-alley-cover-200427141203.jpg', 'https://images.foody.vn/res/g77/765621/prof/s640x400/foody-upload-api-foody-mobile-alley-cover-200427141203.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(3, 2, 'DEAL ĐỘC QUYỀN - KHAO MÓN MỚI')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(11, 3, 'MUA 1 TẶNG 1 - TRÀ LÚA MẠCH (M) TẶNG LỤC TRÀ DÂU (S)', 69000, 300, '', 'https://images.foody.vn/res/g71/700458/s120x120/505be181-48b3-4227-9506-b0c2c0ce-a141238a-220422143616.jpeg', 'https://images.foody.vn/res/g71/700458/s120x120/505be181-48b3-4227-9506-b0c2c0ce-a141238a-220422143616.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(4, 2, 'TRÂN CHÂU ĐƯỜNG ĐEN')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(12, 4, 'Sữa tươi trân châu đường đen', 62000, 300, 'Brown sugar deerioca milk. (Không thể thay đổi mức đường và topping, chỉ thêm được kem trứng)', 'https://images.foody.vn/res/g71/700458/s120x120/988d741d-f41a-4e8f-9fc0-f7a28312ed67.jpg', 'https://images.foody.vn/res/g71/700458/s120x120/988d741d-f41a-4e8f-9fc0-f7a28312ed67.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(13, 4, 'Sữa tươi kem trứng trân châu đường đen', 65000, 300, 'Creme brulee brown sugar deerioca. (Không thể thay đổi mức đường và topping)', 'https://images.foody.vn/res/g71/700458/s120x120/5e9b3341-4163-4c01-c634-a479f11b65f6.jpg', 'https://images.foody.vn/res/g71/700458/s120x120/5e9b3341-4163-4c01-c634-a479f11b65f6.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(14, 4, 'Cacao trân châu đường đen', 65000, 299, 'Brown sugar deerioca cocoa milk', 'https://images.foody.vn/res/g71/700458/s120x120/bccc064f-71a6-4da5-1812-7a0d9aef03db.jpg', 'https://images.foody.vn/res/g71/700458/s120x120/bccc064f-71a6-4da5-1812-7a0d9aef03db.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(15, 4, 'Matcha trân châu đường đen', 65000, 199, 'Brown sugar deerioca matcha latte', 'https://images.foody.vn/res/g71/700458/s120x120/aedf12f1-a876-4461-0116-6f06efe824dd.jpg', 'https://images.foody.vn/res/g71/700458/s120x120/aedf12f1-a876-4461-0116-6f06efe824dd.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(16, 4, 'Latte trân châu đường đen', 75000, 199, 'Size L. Brown sugar deerioca latte. (Không thể thay đổi mức đường và topping)', 'https://images.foody.vn/res/g71/700458/s120x120/ed72f451-47f1-4b29-e6db-0d1ddf158564.jpg', 'https://images.foody.vn/res/g71/700458/s120x120/ed72f451-47f1-4b29-e6db-0d1ddf158564.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(5, 2, 'SỮA CHUA')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(17, 5, 'Sữa chua trân châu đường đen', 65000, 300, 'Mức đá cố định', 'https://images.foody.vn/res/g71/700458/s120x120/ad8b154c-f827-4f13-beb9-e4b9543f5b33.jpg', 'https://images.foody.vn/res/g71/700458/s120x120/ad8b154c-f827-4f13-beb9-e4b9543f5b33.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(18, 5, 'Sữa chua nếp than trân châu đường đen', 68000, 300, 'Mức đá cố định', 'https://images.foody.vn/res/g71/700458/s120x120/0be28233-4e61-4193-aba0-23fe3ca7fff3.jpg', 'https://images.foody.vn/res/g71/700458/s120x120/0be28233-4e61-4193-aba0-23fe3ca7fff3.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(19, 5, 'Sữa chua nếp than', 65000, 299, 'Mức đá cố định', 'https://images.foody.vn/res/g71/700458/s120x120/add16694-df23-4c42-a05f-ea0e04100548.jpg', 'https://images.foody.vn/res/g71/700458/s120x120/add16694-df23-4c42-a05f-ea0e04100548.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(20, 5, 'Sữa chua matcha nếp than trân châu đường đen', 68000, 199, 'Mức đá cố định', 'https://images.foody.vn/res/g71/700458/s120x120/f5d2b4bd-57db-4a03-be8b-9e7903eb5898.jpg', 'https://images.foody.vn/res/g71/700458/s120x120/f5d2b4bd-57db-4a03-be8b-9e7903eb5898.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(21, 5, 'Sữa chua matcha trân châu đường đen', 65000, 199, 'Mức đá cố định', 'https://images.foody.vn/res/g71/700458/s120x120/51c035bc-d9a9-40b6-8386-0f1d38440d8b.jpg', 'https://images.foody.vn/res/g71/700458/s120x120/51c035bc-d9a9-40b6-8386-0f1d38440d8b.jpg')");

        //Restaurant 03
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RESTAURANTS + " VALUES(3, '3 Râu - Gà Rán, Pizza, Trà Sữa & Xiên Que - Quang Trung', '382 Quang Trung, P. 10, Gò Vấp, TP. HCM', 100, 'https://images.foody.vn/res/g98/976661/prof/s640x400/file_restaurant_photo_fr4f_16079-0ce3e036-201214234300.jpeg', 'https://images.foody.vn/res/g98/976661/prof/s640x400/file_restaurant_photo_fr4f_16079-0ce3e036-201214234300.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(6, 3, 'GÀ RÁN 3 RÂU')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(22, 6, 'Khoai Lang Chiên', 22000, 300, '', 'https://images.foody.vn/res/g90/890572/s120x120/3852c42e-184f-46d9-98f6-8fc697db-def39396-201201220318.jpeg', 'https://images.foody.vn/res/g90/890572/s120x120/3852c42e-184f-46d9-98f6-8fc697db-def39396-201201220318.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(23, 6, 'Khoai Tây Chiên - Phần Lớn', 37000, 300, '', 'https://images.foody.vn/res/g90/890572/s120x120/2f3781be-7d49-470a-8caa-64ac7171-ccbe9c05-200827101902.jpeg', 'https://images.foody.vn/res/g90/890572/s120x120/2f3781be-7d49-470a-8caa-64ac7171-ccbe9c05-200827101902.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(24, 6, 'Gà Viên Phủ Sốt Mayonaise', 59000, 299, '', 'https://images.foody.vn/res/g90/890572/s120x120/e268cd3c-6837-4911-86e7-7d5c6100-b91d24b9-200929155601.jpeg', 'https://images.foody.vn/res/g90/890572/s120x120/e268cd3c-6837-4911-86e7-7d5c6100-b91d24b9-200929155601.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(25, 6, 'Khoai Tây Chiên - Phần Nhỏ', 22000, 199, '', 'https://images.foody.vn/res/g90/890572/s120x120/e1a38974-3ab9-4f7f-8730-c9064e15-5fac845d-211020113334.jpeg', 'https://images.foody.vn/res/g90/890572/s120x120/e1a38974-3ab9-4f7f-8730-c9064e15-5fac845d-211020113334.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(7, 3, 'COMBO GÀ')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(26, 7, 'Combo Gà sốt cay 2 (2 Miếng gà rán sốt cay + Khoai + Pepsi size L )', 85000, 300, 'LUÔN SỬ DỤNG GÀ TƯƠI MỚI MỖI NGÀY', 'https://images.foody.vn/res/g90/890572/s120x120/e16340f1-fd55-44f0-891d-bc2b8a25e3ed.jpg', 'https://images.foody.vn/res/g90/890572/s120x120/e16340f1-fd55-44f0-891d-bc2b8a25e3ed.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(27, 7, 'Combo Má Đùi Gà Rán - Khoai - Nước tùy chọn', 52000, 300, 'LUÔN SỬ DỤNG GÀ TƯƠI MỚI MỖI NGÀY', 'https://images.foody.vn/res/g90/890572/s120x120/b8293b23-6fe2-402b-b1d6-0802d048-7a08cee8-201017013132.jpeg', 'https://images.foody.vn/res/g90/890572/s120x120/b8293b23-6fe2-402b-b1d6-0802d048-7a08cee8-201017013132.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(28, 7, 'Combo Gà rán 2 ( 2 miếng gà rán + Pepsi size L + Khoai ) - Fried Chicken', 79000, 299, 'LUÔN SỬ DỤNG GÀ TƯƠI MỚI MỖI NGÀY', 'https://images.foody.vn/res/g90/890572/s120x120/69f73ac9-9f3f-4809-8200-21e9cab5-4b89eeff-201017013235.jpeg', 'https://images.foody.vn/res/g90/890572/s120x120/69f73ac9-9f3f-4809-8200-21e9cab5-4b89eeff-201017013235.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(29, 7, 'Combo Gà sốt ngọt 2 (2 Miếng gà sốt chua ngọt + Khoai+ Pepsi size L )', 85000, 199, 'LUÔN SỬ DỤNG GÀ TƯƠI MỚI MỖI NGÀY', 'https://images.foody.vn/res/g90/890572/s120x120/e7fd3e5a-914e-4b2a-8cd4-097624bc060f.jpg', 'https://images.foody.vn/res/g90/890572/s120x120/e7fd3e5a-914e-4b2a-8cd4-097624bc060f.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(30, 7, 'Combo Gà rán 1 (1 Miếng gà rán + Khoai + 1 nước tự chọn) - Fried Chicken', 45600, 199, 'LUÔN SỬ DỤNG GÀ TƯƠI MỚI MỖI NGÀY', 'https://images.foody.vn/res/g90/890572/s120x120/2507630d-c594-4054-8d49-0b4844b94c49.jpg', 'https://images.foody.vn/res/g90/890572/s120x120/2507630d-c594-4054-8d49-0b4844b94c49.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(31, 7, 'Combo 1 đùi gà rán + Khoai + 1 Coca-Cola', 49000, 199, 'LUÔN SỬ DỤNG GÀ TƯƠI MỚI MỖI NGÀY', 'https://images.foody.vn/res/g90/890572/s120x120/7289bf7e-0955-41ea-9dd0-7689072e-9940c601-210505214202.jpeg', 'https://images.foody.vn/res/g90/890572/s120x120/7289bf7e-0955-41ea-9dd0-7689072e-9940c601-210505214202.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(32, 7, 'Combo 1 má đùi gà rán + Khoai + 1 Coca-Cola', 49000, 199, 'LUÔN SỬ DỤNG GÀ TƯƠI MỚI MỖI NGÀY', 'https://images.foody.vn/res/g90/890572/s120x120/75c0351b-7319-49ad-83e0-3ebcff57-52aed5fc-210505214149.jpeg', 'https://images.foody.vn/res/g90/890572/s120x120/75c0351b-7319-49ad-83e0-3ebcff57-52aed5fc-210505214149.jpeg')");

        //Restaurant 04
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RESTAURANTS + " VALUES(4, 'BÚN ĐẬU GÁNH - Bún Đậu Mắm Tôm - Đường Số 4', '76 Đường Số 4, P. 7, Gò Vấp, TP. HCM', 100, 'https://images.foody.vn/res/g100004/1000036809/prof/s640x400/file_restaurant_photo_q5st_16365-f19650fc-211110160055.jpeg', 'https://images.foody.vn/res/g100004/1000036809/prof/s640x400/file_restaurant_photo_q5st_16365-f19650fc-211110160055.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(8, 4, 'MÓN CHÍNH')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(33, 8, 'Mẹt đặc biệt 1 người', 70000, 300, 'Bún đậu dồi, thịt bắp chả cốm, nem, chả ram tôm đất ...', 'https://images.foody.vn/res/g100004/1000036809/s120x120/33b2584c-dac8-4576-8048-084951a1-fdff0215-211109130207.jpeg', 'https://images.foody.vn/res/g100004/1000036809/s120x120/33b2584c-dac8-4576-8048-084951a1-fdff0215-211109130207.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(34, 8, 'Mẹt thập cẩm', 55000, 300, 'Bún, đậu, chả cốm nem, chả ram tôm đất..', 'https://images.foody.vn/res/g100004/1000036809/s120x120/4f090237-7fd6-449f-a58e-d0c9e021-70a9f5dc-211109125747.jpeg', 'https://images.foody.vn/res/g100004/1000036809/s120x120/4f090237-7fd6-449f-a58e-d0c9e021-70a9f5dc-211109125747.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(35, 8, 'Mẹt đặc biệt 2 người', 140000, 299, 'Bún đậu dồi thịt bắp, chả ram tôm đất, nem, chả cốm...', 'https://images.foody.vn/res/g100004/1000036809/s120x120/33b2584c-dac8-4576-8048-084951a1-fdff0215-211109130207.jpeg', 'https://images.foody.vn/res/g100004/1000036809/s120x120/33b2584c-dac8-4576-8048-084951a1-fdff0215-211109130207.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(36, 8, 'Bún đậu chả cốm', 39000, 199, '', 'https://images.foody.vn/res/g100004/1000036809/s120x120/4f090237-7fd6-449f-a58e-d0c9e021-70a9f5dc-211109125747.jpeg', 'https://images.foody.vn/res/g100004/1000036809/s120x120/4f090237-7fd6-449f-a58e-d0c9e021-70a9f5dc-211109125747.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(37, 8, 'Bún đậu', 27000, 300, '', 'https://images.foody.vn/res/g100004/1000036809/s120x120/3250d321-f7bd-4e6a-a48c-4bdcd8e5-5fc8031c-211109125627.jpeg', 'https://images.foody.vn/res/g100004/1000036809/s120x120/3250d321-f7bd-4e6a-a48c-4bdcd8e5-5fc8031c-211109125627.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(38, 8, 'Bún đậu giò heo', 40000, 300, '', 'https://images.foody.vn/res/g100004/1000036809/s120x120/d49ff27c-fe4e-4047-bfd1-fe08457f-232ebdf2-211109125814.jpeg', 'https://images.foody.vn/res/g100004/1000036809/s120x120/d49ff27c-fe4e-4047-bfd1-fe08457f-232ebdf2-211109125814.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(39, 8, 'Bún đậu chả sụn', 40000, 299, '', 'https://images.foody.vn/res/g100004/1000036809/s120x120/d49ff27c-fe4e-4047-bfd1-fe08457f-232ebdf2-211109125814.jpeg', 'https://images.foody.vn/res/g100004/1000036809/s120x120/d49ff27c-fe4e-4047-bfd1-fe08457f-232ebdf2-211109125814.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(40, 8, 'Bún đậu chả cua', 40000, 199, '', 'https://images.foody.vn/res/g100004/1000036809/s120x120/aeefbcf4-2450-475c-8b7f-13d37753-9c896d48-211109125712.jpeg', 'https://images.foody.vn/res/g100004/1000036809/s120x120/aeefbcf4-2450-475c-8b7f-13d37753-9c896d48-211109125712.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(9, 4, 'NƯỚC')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(41, 9, 'Trà tắc', 12000, 300, '', 'https://images.foody.vn/res/g100004/1000036809/s120x120/1ef25ef7-054b-4897-b780-571d666f-c9f55cdc-211208122052.jpeg', 'https://images.foody.vn/res/g100004/1000036809/s120x120/1ef25ef7-054b-4897-b780-571d666f-c9f55cdc-211208122052.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(42, 9, 'Nước sấu', 15000, 300, '', 'https://images.foody.vn/res/g100004/1000036809/s120x120/432c9b5f-f0f9-407c-865a-2d227605-dc36420d-211208121948.jpeg', 'https://images.foody.vn/res/g100004/1000036809/s120x120/432c9b5f-f0f9-407c-865a-2d227605-dc36420d-211208121948.jpeg')");

        //Restaurant 05
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RESTAURANTS + " VALUES(5, 'Artisan Baked - Bánh Biscotti - Nguyễn Duy', '522/525 Nguyễn Duy, P. 10,  Quận 8, TP. HCM', 100, 'https://images.foody.vn/res/g106/1053903/prof/s640x400/file_restaurant_photo_pruz_16331-ed3d1bb3-211002195408.jpeg', 'https://images.foody.vn/res/g106/1053903/prof/s640x400/file_restaurant_photo_pruz_16331-ed3d1bb3-211002195408.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(10, 5, 'BÁNH BISCOTTI')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(43, 10, 'Bánh Biscotti Dark Chocolate - Hộp 195gr', 74250, 300, 'Bánh Biscotti hạnh nhân & socola nguyên chất, dành cho những người sành ăn tạo ra một sự kết hợp ngon miệng.', 'https://images.foody.vn/res/g105/1044820/s120x120/b4181034-6bc9-4eb6-8759-ad34fd76-37d537ed-201007122820.jpeg', 'https://images.foody.vn/res/g105/1044820/s120x120/b4181034-6bc9-4eb6-8759-ad34fd76-37d537ed-201007122820.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(44, 10, 'Bánh Biscotti Truyền Thống - Hộp 195gr', 74250, 300, 'Hương vị truyền thống kết hợp hạt hạnh nhân & óc chó với một chút hương vị hạt tiểu hồi.', 'https://images.foody.vn/res/g105/1044820/s120x120/72ce1d39-c8fd-47f9-88b0-d8a9b85a-2ced17ea-201007122133.jpeg', 'https://images.foody.vn/res/g105/1044820/s120x120/72ce1d39-c8fd-47f9-88b0-d8a9b85a-2ced17ea-201007122133.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(45, 10, 'Biscotti Nam Việt Quất - Hộp 195gr', 90000, 299, 'Nam Việt Quất kết hợp với Hạnh Nhân và được nướng hai lần mang đến cho bạn cảm giác dai giòn ngon tuyệt đỉnh.', 'https://images.foody.vn/res/g105/1044820/s120x120/8d804da0-cdd2-4376-a274-a536e7dc-c3df0599-201007123321.jpeg', 'https://images.foody.vn/res/g105/1044820/s120x120/8d804da0-cdd2-4376-a274-a536e7dc-c3df0599-201007123321.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(46, 10, 'Biscotti Hạt Dẻ Cười - Hộp 195gr', 90000, 199, 'Hạt dẻ cười thơm ngon trộn với hạnh nhân và được nướng hai lần giúp hoàn thiện bánh biscotti mỏng và giòn tan.', 'https://images.foody.vn/res/g105/1044820/s120x120/8ad0d90c-0266-4fa6-87ee-2049528d-679f3b8c-201007123854.jpeg', 'https://images.foody.vn/res/g105/1044820/s120x120/8ad0d90c-0266-4fa6-87ee-2049528d-679f3b8c-201007123854.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(47, 10, 'Biscotti Nho, Dừa - Hộp 195gr', 90000, 300, 'Nho khô ngọt thanh kết hợp cùng vị béo của dừa nướng và giòn tan của hạnh nhân quyện với hương quế tròn vị tinh tế. Bánh mỏng giòn và rất gây nghiện.', 'https://images.foody.vn/res/g105/1044820/s120x120/56ef00f4-04c4-4751-a87b-e9ea89b2-64244105-201007124351.jpeg', 'https://images.foody.vn/res/g105/1044820/s120x120/56ef00f4-04c4-4751-a87b-e9ea89b2-64244105-201007124351.jpeg')");

        //Restaurant 06
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RESTAURANTS + " VALUES(6, 'Kim Tước - Cơm Tấm & Bún Thịt Nướng - Nguyễn Đình Chiểu', '338/17 Nguyễn Đình Chiểu, P. 4,  Quận 3, TP. HCM', 100, 'https://images.foody.vn/res/g114/1134239/prof/s640x400/foody-upload-api-foody-mobile-im-c779b555-220419211129.jpeg', 'https://images.foody.vn/res/g114/1134239/prof/s640x400/foody-upload-api-foody-mobile-im-c779b555-220419211129.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(11, 6, 'CƠM SƯỜN')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(48, 11, 'Cơm tấm sườn', 39200, 300, 'Cơm tấm, 1 canh thêm và sườn nướng. Sườn được ướp qua đêm với gia vị và quét sốt mật ong khi nướng, tạo được mùi vị thơm ngọt béo', 'https://images.foody.vn/res/g114/1134239/s120x120/16b0c3d1-8000-4797-a2c2-c3509e8b-5a6dcf5a-220423171746.jpeg', 'https://images.foody.vn/res/g114/1134239/s120x120/16b0c3d1-8000-4797-a2c2-c3509e8b-5a6dcf5a-220423171746.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(49, 11, 'Cơm Tấm Sườn Trứng Ốp La', 42400, 300, 'cơm tấm, 1 canh thêm, sườn và trứng ốp la. Sườn được ướp qua đêm với gia vị và quét sốt mật ong khi nướng, tạo mùi vị thơm, ngọt béo', 'https://images.foody.vn/res/g114/1134239/s120x120/55d061c2-d3f5-4870-9841-670a4f13-7e312258-220423171708.jpeg', 'https://images.foody.vn/res/g114/1134239/s120x120/55d061c2-d3f5-4870-9841-670a4f13-7e312258-220423171708.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(50, 11, 'Cơm tấm sườn bì chả', 50400, 299, 'Cơm tấm, 1 canh thêm, sườn, bì và chả. Sườn được ướp qua đêm với gia vị và quét sốt mật ong khi nướng tạo được mùi vị thơm ngọt béo', 'https://images.foody.vn/res/g114/1134239/s120x120/ce28ffbf-296d-423d-b21d-f17a1bad-41890b71-220423171829.jpeg', 'https://images.foody.vn/res/g114/1134239/s120x120/ce28ffbf-296d-423d-b21d-f17a1bad-41890b71-220423171829.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(12, 6, 'CƠM TẤM BA RỌI')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(51, 12, 'Cơm tấm ba rọi nướng trứng ốp la', 42400, 300, 'Cơm tấm, 1 Canh thêm, Ba rọi nướng và Trứng ốp la', 'https://images.foody.vn/res/g114/1134239/s120x120/222edad5-5094-438b-8568-08c040c3-251e5b24-220423172345.jpeg', 'https://images.foody.vn/res/g114/1134239/s120x120/222edad5-5094-438b-8568-08c040c3-251e5b24-220423172345.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(52, 12, 'Cơm tấm ba rọi nướng', 39200, 300, 'Cơm tấm, 1 canh thêm và ba rọi', 'https://images.foody.vn/res/g114/1134239/s120x120/4fe4841f-653a-4b84-a5a2-a91dbed8-6260f06f-220423172254.jpeg', 'https://images.foody.vn/res/g114/1134239/s120x120/4fe4841f-653a-4b84-a5a2-a91dbed8-6260f06f-220423172254.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(53, 12, 'Cơm Tấm Ba Rọi Chả', 42400, 299, '', 'https://images.foody.vn/res/g114/1134239/s120x120/7bab9d28-a6a9-4d68-966b-47573247-0b10c0c4-220423174442.jpeg', 'https://images.foody.vn/res/g114/1134239/s120x120/7bab9d28-a6a9-4d68-966b-47573247-0b10c0c4-220423174442.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(54, 12, 'Cơm Tấm Ba Rọi Nem Lụi', 47200, 299, 'Bao gồm 1 canh thêm, cơm, ba rọi và nem lụi', 'https://images.foody.vn/res/g114/1134239/s120x120/f367119e-5961-49e6-a32b-7afed98d-d27cd53c-220423172008.jpeg', 'https://images.foody.vn/res/g114/1134239/s120x120/f367119e-5961-49e6-a32b-7afed98d-d27cd53c-220423172008.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(13, 6, 'CƠM TẤM SƯỜN CÂY')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(55, 13, 'Cơm Tấm Sườn Cây Trứng Ốp La', 42400, 300, 'Cơm tấm, 1 canh thêm, sườn cây và trứng ốp la', 'https://images.foody.vn/res/g114/1134239/s120x120/f2e883f1-070c-44ab-9125-31c6a2b5-4aec7461-220423174844.jpeg', 'https://images.foody.vn/res/g114/1134239/s120x120/f2e883f1-070c-44ab-9125-31c6a2b5-4aec7461-220423174844.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(56, 13, 'Cơm Tấm Sườn Cây', 39200, 300, 'Cơm tấm, 1 canh thêm và sườn cây nướng', 'https://images.foody.vn/res/g114/1134239/s120x120/869ac6de-9202-4784-9740-ba93cdf2-659115f9-220423174742.jpeg', 'https://images.foody.vn/res/g114/1134239/s120x120/869ac6de-9202-4784-9740-ba93cdf2-659115f9-220423174742.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(57, 13, 'Cơm Tấm Sườn Cây Chả', 42400, 299, 'Cơm tấm, 1 canh thêm, sườn cây và chả', 'https://images.foody.vn/res/g114/1134239/s120x120/e2a7e2ce-ebf0-4794-a9eb-00f358b9-cc24a6b7-220423174938.jpeg', 'https://images.foody.vn/res/g114/1134239/s120x120/e2a7e2ce-ebf0-4794-a9eb-00f358b9-cc24a6b7-220423174938.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(58, 13, 'Cơm Tấm Sườn Cây Nem Lụi', 42400, 299, 'Cơm tấm, 1 canh thêm, sườn cây và nem lụi', 'https://images.foody.vn/res/g114/1134239/s120x120/45d06cbd-346f-49a8-8763-6a41cf68-44a9882e-220423174816.jpeg', 'https://images.foody.vn/res/g114/1134239/s120x120/45d06cbd-346f-49a8-8763-6a41cf68-44a9882e-220423174816.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(14, 6, 'CƠM TẤM NEM LỤI')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(59, 14, 'Cơm Tấm Nem Lụi', 39200, 300, 'Cơm Tấm Nem Lụi', 'https://images.foody.vn/res/g114/1134239/s120x120/07145057-49c1-43c3-bfa2-5ea6a123-cb57e447-220420163039.jpeg', 'https://images.foody.vn/res/g114/1134239/s120x120/07145057-49c1-43c3-bfa2-5ea6a123-cb57e447-220420163039.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(15, 6, 'THÊM')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(60, 15, 'Cơm Thêm', 4000, 300, '', 'https://images.foody.vn/res/g114/1134239/s120x120/119355e0-2b08-44c4-b55e-fe680629-035fcd84-220421201552.jpeg', 'https://images.foody.vn/res/g114/1134239/s120x120/119355e0-2b08-44c4-b55e-fe680629-035fcd84-220421201552.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(61, 15, 'Canh Thêm ( Rau Ngót Thịt Bằm)', 4000, 300, '', 'https://images.foody.vn/res/g114/1134239/s120x120/fa970926-7153-45bf-81d9-efc68f5a-c679fd9c-220423175009.jpeg', 'https://images.foody.vn/res/g114/1134239/s120x120/fa970926-7153-45bf-81d9-efc68f5a-c679fd9c-220423175009.jpeg')");

        //Restaurant 07
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RESTAURANTS + " VALUES(7, 'SaSin - Mì Cay 7 Cấp Độ Hàn Quốc - D2', '11 Đường D2, P. 25,  Quận Bình Thạnh, TP. HCM', 100, 'https://images.foody.vn/res/g24/233099/prof/s640x400/foody-upload-api-foody-mobile-2-200408094158.jpg', 'https://images.foody.vn/res/g24/233099/prof/s640x400/foody-upload-api-foody-mobile-2-200408094158.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(16, 7, 'LẨU 4 NGƯỜI')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(62, 16, 'Lẩu thái bò Úc (4 người)', 395000, 100, '', 'https://images.foody.vn/res/g24/233099/s120x120/81e91d7c-e3f9-4827-9c85-2c02073a-831925c7-220504114812.jpeg', 'https://images.foody.vn/res/g24/233099/s120x120/81e91d7c-e3f9-4827-9c85-2c02073a-831925c7-220504114812.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(63, 16, 'Lẩu tomyum bò Mỹ (4 người)', 419000, 100, '', 'https://images.foody.vn/res/g24/233099/s120x120/81e91d7c-e3f9-4827-9c85-2c02073a-831925c7-220504114812.jpeg', 'https://images.foody.vn/res/g24/233099/s120x120/81e91d7c-e3f9-4827-9c85-2c02073a-831925c7-220504114812.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(17, 7, 'MÌ CAY')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(64, 17, 'Mì kim chi hải sản', 68000, 100, '', 'https://images.foody.vn/res/g24/233099/s120x120/af75c208-98d2-4549-97b6-5a161842f80e.jpg', 'https://images.foody.vn/res/g24/233099/s120x120/af75c208-98d2-4549-97b6-5a161842f80e.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(65, 17, 'Mì kim chi bò Mỹ', 68000, 100, '', 'https://images.foody.vn/res/g24/233099/s120x120/5ea49d3d-c85e-4b6f-9701-65645305bfcc.jpg', 'https://images.foody.vn/res/g24/233099/s120x120/5ea49d3d-c85e-4b6f-9701-65645305bfcc.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(66, 17, 'Mì kim chi gà', 68000, 100, '', 'https://images.foody.vn/res/g24/233099/s120x120/72e37e19-5b25-44e2-9dba-cf29382e86de.jpg', 'https://images.foody.vn/res/g24/233099/s120x120/72e37e19-5b25-44e2-9dba-cf29382e86de.jpg')");

        //Restaurant 08
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RESTAURANTS + " VALUES(8, 'Cơm Chiên & Nui Xào Bò - Cống Quỳnh', '102/12 Cống Quỳnh, Quận 1, TP. HCM', 100, 'https://images.foody.vn/res/g4/38121/prof/s640x400/foody-mobile-w487r6a0-jpg-439-635858718115795391.jpg', 'https://images.foody.vn/res/g4/38121/prof/s640x400/foody-mobile-w487r6a0-jpg-439-635858718115795391.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(18, 8, 'NUI CHIÊN')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(67, 18, 'Nui bò lúc lắc', 37000, 300, '', 'https://images.foody.vn/res/g4/38121/s120x120/101f075e-da4b-4c93-88e0-43d2ab75b25b.jpeg', 'https://images.foody.vn/res/g4/38121/s120x120/101f075e-da4b-4c93-88e0-43d2ab75b25b.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(68, 18, 'Nui chiên đùi gà', 38000, 300, '', 'https://images.foody.vn/res/g4/38121/s120x120/4239dabb-8dc1-45fc-ba53-46a7ac9c4461.jpeg', 'https://images.foody.vn/res/g4/38121/s120x120/4239dabb-8dc1-45fc-ba53-46a7ac9c4461.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(19, 8, 'KHOAI CHIÊN')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(69, 19, 'Khoai chiên bò lúc lắc', 42000, 300, '', 'https://images.foody.vn/res/g4/38121/s120x120/176e2764-a5c9-497a-afbb-f10bead4faf8.jpeg', 'https://images.foody.vn/res/g4/38121/s120x120/176e2764-a5c9-497a-afbb-f10bead4faf8.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(70, 19, 'Khoai Tây Bơ Đườg', 35000, 300, '', 'https://images.foody.vn/res/g4/38121/s120x120/176e2764-a5c9-497a-afbb-f10bead4faf8.jpeg', 'https://images.foody.vn/res/g4/38121/s120x120/176e2764-a5c9-497a-afbb-f10bead4faf8.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(20, 8, 'CƠM CHIÊN')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(71, 20, 'Cơm bò lúc lắc', 37000, 300, '', 'https://images.foody.vn/res/g4/38121/s120x120/fe8a4ecd-1c4a-46f6-8bcd-e38c24dc521a.jpeg', 'https://images.foody.vn/res/g4/38121/s120x120/fe8a4ecd-1c4a-46f6-8bcd-e38c24dc521a.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(72, 20, 'Cơm chiên dương châu bò', 45000, 300, '', 'https://images.foody.vn/res/g4/38121/s120x120/3bdf6dbc-e9be-4aaa-af5c-48cd1c2f9bc2.jpeg', 'https://images.foody.vn/res/g4/38121/s120x120/3bdf6dbc-e9be-4aaa-af5c-48cd1c2f9bc2.jpeg')");

        //Restaurant 09
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RESTAURANTS + " VALUES(9, 'Heo Con - Gà Rán, Hamburger & Sushi', '16 Đường D3, KP. 1, P. Linh Tây, Thủ Đức, TP. HCM', 100, 'https://images.foody.vn/res/g101/1001799/prof/s640x400/image-02c35b39-200910114133.jpeg', 'https://images.foody.vn/res/g101/1001799/prof/s640x400/image-02c35b39-200910114133.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(21, 9, 'SIÊU SALE')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(73, 21, 'Hamburger bò/ gà', 28500, 300, '', 'https://images.foody.vn/res/g101/1001799/s120x120/765b18c2-5c77-4208-b134-60c2a3f0d36c.jpg', 'https://images.foody.vn/res/g101/1001799/s120x120/765b18c2-5c77-4208-b134-60c2a3f0d36c.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(74, 21, 'Cơm cuộn sushi', 29450, 300, '8 miếng', 'https://images.foody.vn/res/g101/1001799/s120x120/a24b53eb-0856-4081-a5f8-4ed235b7f9ba.jpg', 'https://images.foody.vn/res/g101/1001799/s120x120/a24b53eb-0856-4081-a5f8-4ed235b7f9ba.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(75, 21, 'Sushi chiên xù', 37050, 299, '', 'https://images.foody.vn/res/g101/1001799/s120x120/2d6df2c3-1a8a-4380-bc9c-fc4c3fe6ae21.jpg', 'https://images.foody.vn/res/g101/1001799/s120x120/2d6df2c3-1a8a-4380-bc9c-fc4c3fe6ae21.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(76, 21, 'Hamburger bo/ ga +phô mai', 90000, 199, '', 'https://images.foody.vn/res/g101/1001799/s120x120/97d076a7-9d1c-4c1a-bc9c-924b6c8fd934.jpg', 'https://images.foody.vn/res/g101/1001799/s120x120/97d076a7-9d1c-4c1a-bc9c-924b6c8fd934.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(77, 21, 'Hamburger( bò/ gà) + trứng', 90000, 300, '', 'https://images.foody.vn/res/g101/1001799/s120x120/fd25c36e-b1ea-44e2-bb31-591b3396f156.jpeg', 'https://images.foody.vn/res/g101/1001799/s120x120/fd25c36e-b1ea-44e2-bb31-591b3396f156.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(78, 21, 'Combo 12: Gà Viên + Khoai Lang Lắc', 47500, 199, 'Khoai Lang lắc', 'https://images.foody.vn/res/g101/1001799/s120x120/1d8c7011-1618-40fd-8262-ccfc963cbe45.jpeg', 'https://images.foody.vn/res/g101/1001799/s120x120/1d8c7011-1618-40fd-8262-ccfc963cbe45.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(79, 21, 'Combo2-1 miếng gà ngẫu nhiên + khoai lang lắc + nước ngọt', 54150, 300, 'Gà ngẫu nhiên , Khoai lang lắc', 'https://images.foody.vn/res/g101/1001799/s120x120/0c6ce233-170c-4f0b-bc44-1dbaf282-68f66f12-211025132814.jpeg', 'https://images.foody.vn/res/g101/1001799/s120x120/0c6ce233-170c-4f0b-bc44-1dbaf282-68f66f12-211025132814.jpeg')");

        //Restaurant 10
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RESTAURANTS + " VALUES(10, 'Thanh Hương - Cháo Lòng Hà Nội - Đoàn Văn Bơ', '416 Đoàn Văn Bơ, P. 14, Quận 4, TP. HCM', 100, 'https://images.foody.vn/res/g98/975683/prof/s640x400/foody-upload-api-foody-mobile-foody-upload-api-foo-191101144817.jpg', 'https://images.foody.vn/res/g98/975683/prof/s640x400/foody-upload-api-foody-mobile-foody-upload-api-foo-191101144817.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(22, 10, 'MÓN ĐANG GIẢM')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(80, 22, 'Đĩa Lòng Không Cháo Phần Lớn.', 279000, 199, 'lưỡi.tim.lòng .dồi.gan.dạ dày.cuống họng..vv', 'https://images.foody.vn/res/g98/975683/s120x120/3f4e634c-5db1-4406-9f40-1b7d635b-59dedbd0-201117230538.jpeg', 'https://images.foody.vn/res/g98/975683/s120x120/3f4e634c-5db1-4406-9f40-1b7d635b-59dedbd0-201117230538.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(23, 10, 'MỜI MÓN NGON - GIẢM NỬA GIÁ')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(81, 23, 'Combo Cháo Huyết + Cháo Dồi phần nhỏ', 49000, 299, '', 'https://images.foody.vn/res/g98/975683/s120x120/fb69f90a-3cdc-4e60-9d77-70bb0a78-3faab10b-220321120725.jpeg', 'https://images.foody.vn/res/g98/975683/s120x120/fb69f90a-3cdc-4e60-9d77-70bb0a78-3faab10b-220321120725.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(24, 10, 'MỜI BẠN MÓN NGON')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(82, 24, 'Đĩa Lòng Không Cháo Phần Lớn.', 279000, 299, 'lưỡi.tim.lòng .dồi.gan.dạ dày.cuống họng..vv', 'https://images.foody.vn/res/g98/975683/s120x120/3f4e634c-5db1-4406-9f40-1b7d635b-59dedbd0-201117230538.jpeg', 'https://images.foody.vn/res/g98/975683/s120x120/3f4e634c-5db1-4406-9f40-1b7d635b-59dedbd0-201117230538.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(25, 10, 'COMBO GIẢM SÂU MỜI BẠN NĂM MỚI')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(83, 25, 'Combo Mời Bạn Tháng 4 Hoành Tráng', 195000, 299, 'Gồm: 1 Phần cháo Tim, 1 dĩa lòng không cháo', 'https://images.foody.vn/res/g98/975683/s120x120/b350b59e-eabf-43cd-808c-c538b31d-b5374eb8-211202130711.jpeg', 'https://images.foody.vn/res/g98/975683/s120x120/b350b59e-eabf-43cd-808c-c538b31d-b5374eb8-211202130711.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(84, 25, 'Combo Mời cả nhà Tháng 4', 335000, 299, 'Đĩa Lòng không cháo phần lớn + 1 Cháo Huyết', 'https://images.foody.vn/res/g98/975683/s120x120/6a1bef80-6966-4014-93e4-c049f75f-a72c9e8f-211115141015.jpeg', 'https://images.foody.vn/res/g98/975683/s120x120/6a1bef80-6966-4014-93e4-c049f75f-a72c9e8f-211115141015.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(85, 25, 'Combo Tiệc tiện lợi', 335000, 299, 'Cháo Lòng Phần 2 Người + Cháo 1 Phần Cháo Tim 1 Phần Cháo Dồi', 'https://images.foody.vn/res/g98/975683/s120x120/ec609024-0422-4d73-9094-6c480d07-669a39e0-211221153125.jpeg', 'https://images.foody.vn/res/g98/975683/s120x120/ec609024-0422-4d73-9094-6c480d07-669a39e0-211221153125.jpeg')");

        //Restaurant 11
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RESTAURANTS + " VALUES(11, 'TocoToco Bubble Tea - Đường Số 17', '103 Đường số 17, P, Tân Kiểng, Quận 7, TP. HCM', 100, 'https://images.foody.vn/res/g101/1004557/prof/s640x400/foody-upload-api-foody-mobile-6-201105111841.jpg', 'https://images.foody.vn/res/g101/1004557/prof/s640x400/foody-upload-api-foody-mobile-6-201105111841.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(26, 11, 'BEST SELLER')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(86, 26, 'Trà sữa trân châu hoàng gia', 36800, 300, '', 'https://images.foody.vn/res/g101/1004557/s120x120/36058fe8-9485-48e6-961d-9bd7956f3db5.jpg', 'https://images.foody.vn/res/g101/1004557/s120x120/36058fe8-9485-48e6-961d-9bd7956f3db5.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(87, 26, 'Trà sữa panda', 38400, 300, '', 'https://images.foody.vn/res/g101/1004557/s120x120/35d7e772-a971-40fc-beee-3b0969e93536.jpg', 'https://images.foody.vn/res/g101/1004557/s120x120/35d7e772-a971-40fc-beee-3b0969e93536.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(88, 26, 'Trà sữa ba anh em', 38400, 299, '', 'https://images.foody.vn/res/g101/1004557/s120x120/7ff9d681-9194-480c-9502-c24c9945ddff.jpg', 'https://images.foody.vn/res/g101/1004557/s120x120/7ff9d681-9194-480c-9502-c24c9945ddff.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(89, 26, 'Sữa tươi trân châu đường hổ', 41600, 199, 'Tiger Sugar', 'https://images.foody.vn/res/g101/1004557/s120x120/7ed67b21-157a-4e76-9f50-c70d73c61c79.jpg', 'https://images.foody.vn/res/g101/1004557/s120x120/7ed67b21-157a-4e76-9f50-c70d73c61c79.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(90, 26, 'Trà sữa kim cương đen Okinawa', 36800, 300, '', 'https://images.foody.vn/res/g101/1004557/s120x120/d6c196d0-be49-4f97-9619-c903c31230ed.jpg', 'https://images.foody.vn/res/g101/1004557/s120x120/d6c196d0-be49-4f97-9619-c903c31230ed.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(91, 26, 'Trà dâu tằm pha lê tuyết', 33600, 299, '', 'https://images.foody.vn/res/g101/1004557/s120x120/f033b957-04be-4819-87ee-3bc2efd49f9f.jpg', 'https://images.foody.vn/res/g101/1004557/s120x120/f033b957-04be-4819-87ee-3bc2efd49f9f.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(92, 26, 'Matcha đậu đỏ', 39200, 199, '', 'https://images.foody.vn/res/g101/1004557/s120x120/c43a0165-e110-494b-8974-d5137e489a2b.jpg', 'https://images.foody.vn/res/g101/1004557/s120x120/c43a0165-e110-494b-8974-d5137e489a2b.jpg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(93, 26, 'Dâu tằm kem phô mai', 38400, 300, '', 'https://images.foody.vn/res/g101/1004557/s120x120/b815d5d1-0587-4c68-ad44-c266a0b5fedb.jpg', 'https://images.foody.vn/res/g101/1004557/s120x120/b815d5d1-0587-4c68-ad44-c266a0b5fedb.jpg')");

        //Restaurant 12
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_RESTAURANTS + " VALUES(12, 'Phố Ăn Vặt - Bánh Tráng Trộn & Cá Viên Chiên', '96 Trần Khắc Chân, P. 9, Phú Nhuận, TP. HCM', 100, 'https://images.foody.vn/res/g114/1134935/prof/s640x400/foody-upload-api-foody-mobile-fo-a258eddb-220426114346.jpeg', 'https://images.foody.vn/res/g114/1134935/prof/s640x400/foody-upload-api-foody-mobile-fo-a258eddb-220426114346.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(27, 12, 'MÓN ĐANG GIẢM')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(94, 27, 'Combo Đồ Chiên Vừa', 35000, 300, '', 'https://images.foody.vn/res/g113/1122304/s120x120/44104e87-e53e-4ece-b934-cd315683-8e0b61cf-220422164704.jpeg', 'https://images.foody.vn/res/g113/1122304/s120x120/44104e87-e53e-4ece-b934-cd315683-8e0b61cf-220422164704.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(28, 12, 'TRÀ')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(95, 28, 'Lục trà chanh', 18000, 300, '', 'https://images.foody.vn/res/g113/1122304/s120x120/aca3f468-2cae-4c34-860c-d508c538-d935df5e-220427230528.jpeg', 'https://images.foody.vn/res/g113/1122304/s120x120/aca3f468-2cae-4c34-860c-d508c538-d935df5e-220427230528.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(29, 12, 'LẨU LY')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(96, 29, 'Lẩu Ly cá viên', 20000, 300, '', 'https://images.foody.vn/res/g113/1122304/s120x120/fccae735-f088-45a4-8b00-7836f9eb-584eaa53-220501062103.jpeg', 'https://images.foody.vn/res/g113/1122304/s120x120/fccae735-f088-45a4-8b00-7836f9eb-584eaa53-220501062103.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(97, 29, 'Lẩu ly đặc biệt', 40000, 300, '', 'https://images.foody.vn/res/g113/1122304/s120x120/9b2e134e-4dbd-461a-84de-99b2e6fb-f9f360fd-220501230913.jpeg', 'https://images.foody.vn/res/g113/1122304/s120x120/9b2e134e-4dbd-461a-84de-99b2e6fb-f9f360fd-220501230913.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS_MENU + " VALUES(30, 12, 'CÁ VIÊN CHIÊN')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(98, 30, 'Xúc xích hồ lô', 10000, 300, '3 Viên', 'https://images.foody.vn/res/g113/1122304/s120x120/7db3de75-f381-4e39-99cd-e8eacbc3-ba8c613c-220108175835.jpeg', 'https://images.foody.vn/res/g113/1122304/s120x120/7db3de75-f381-4e39-99cd-e8eacbc3-ba8c613c-220108175835.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(99, 30, 'Bò viên', 6000, 300, '5 Viên', 'https://images.foody.vn/res/g113/1122304/s120x120/05c5af9b-c644-44ac-b9f3-28e70020-d65bfc9e-220108151740.jpeg', 'https://images.foody.vn/res/g113/1122304/s120x120/05c5af9b-c644-44ac-b9f3-28e70020-d65bfc9e-220108151740.jpeg')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_FOODS + " VALUES(100, 30, 'Cá viên', 6000, 300, '5 Viên', 'https://images.foody.vn/res/g113/1122304/s120x120/a3da2cb7-1966-464f-b334-110eae31-40893cc9-220108174329.jpeg', 'https://images.foody.vn/res/g113/1122304/s120x120/a3da2cb7-1966-464f-b334-110eae31-40893cc9-220108174329.jpeg')");

        //User default
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_USERS + " VALUES(1, 'Vinh & Trường', 'admin', '123', 'Số 1, Võ Văn Ngăn, Thủ Đức, TP.Hồ Chí Minh', '0123456789')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_ORDER_DETAILS + "'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_ORDERS + "'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_USERS + "'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_FOODS + "'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_FOODS_MENU + "'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_RESTAURANTS + "'");
        onCreate(sqLiteDatabase);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    //SQl for Foods
    public ArrayList<Food> getAllFoodsByIdFoodMenu(int id) {
        ArrayList<Food> foodArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FOODS + " WHERE " + FOREIGN_KEY_REF_FOOD_MENU + " = ? ORDER BY " + KEY_ID + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[] {Integer.toString(id)});
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Food food = new Food();
            food.setFoodId(cursor.getInt(0));
            food.setName(cursor.getString(2));
            food.setPrice(Integer.toString(cursor.getInt(3)));
            food.setSold(cursor.getInt(4));
            food.setDescription(cursor.getString(5));
            food.setCoverImage(cursor.getString(6));
            food.setThumbImage(cursor.getString(7));

            foodArrayList.add(food);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return foodArrayList;
    }

    public Food getFoodById(int foodId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FOODS, null, KEY_ID + " = ?", new String[] { String.valueOf(foodId)}, null, null, null);
        Food food = null;

        if (cursor.moveToFirst()) {
            food = new Food();
            food.setFoodId(cursor.getInt(0));
            food.setName(cursor.getString(2));
            food.setPrice(Integer.toString(cursor.getInt(3)));
            food.setSold(cursor.getInt(4));
            food.setDescription(cursor.getString(5));
            food.setCoverImage(cursor.getString(6));
            food.setThumbImage(cursor.getString(7));
        }
        cursor.close();
        db.close();
        return food;
    }

    //SQL for Food_Menu
    public ArrayList<Menu> getAllMenuFoodsByIdRestaurant(int id) {
        ArrayList<Menu> menuArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FOODS_MENU + " WHERE " + FOREIGN_KEY_REF_RESTAURANT + " = ? ORDER BY " + KEY_ID + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[] {Integer.toString(id)});
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Menu menu = new Menu();
            menu.setId(cursor.getInt(0));
            menu.setTitle(cursor.getString(2));
            menu.setFoods(getAllFoodsByIdFoodMenu(cursor.getInt(0)));

            menuArrayList.add(menu);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return menuArrayList;
    }

    //SQl for Orders
    public void changeOrderStatus(int status, int orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "UPDATE " + TABLE_ORDERS +
                " SET " + KEY_PAID + " = " + status +
                " WHERE " + KEY_ID + " = " + orderId;
        db.execSQL(selectQuery);
        db.close();
    }

    public Order checkExistNotPaidOrder(int userId, int restaurantId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ORDERS, null, FOREIGN_KEY_REF_USER + " = ? AND " + KEY_PAID + " = ? AND " + FOREIGN_KEY_REF_RESTAURANT + " = ?", new String[] { String.valueOf(userId), String.valueOf(0), String.valueOf(restaurantId)}, null, null, null);
        Order order = null;

        if (cursor.moveToFirst()) {
            order = new Order();
            order.setId(cursor.getInt(0));
            order.setUserId(cursor.getInt(1));
            order.setRestaurantId(cursor.getInt(2));
            order.setPaid(cursor.getInt(3));
            order.setUserName(cursor.getString(4));
            order.setPhone(cursor.getString(5));
            order.setAddress(cursor.getString(6));
            order.setCreatedAt(cursor.getString(7));
        }
        cursor.close();
        db.close();
        Log.d("checkExistOrder", order != null ? order.toString(): "null");
        return order;
    }

    public Order createNewOrder(int userId, int restaurantId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(FOREIGN_KEY_REF_USER, userId);
        values.put(FOREIGN_KEY_REF_RESTAURANT, restaurantId);
        long rowInserted = db.insert(TABLE_ORDERS, null, values);
        Order order = null;

        if (rowInserted != -1) {
            order = this.getOrderById(rowInserted);
        } else {
            Toast.makeText(context, "Đã xảy ra lỗi trong quá trình tạo 'Đơn hàng'", Toast.LENGTH_LONG).show();
        }
        db.close();
        return order;
    }

    public void deleteOrder(int orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDERS, KEY_ID + " = ?", new String[] { String.valueOf(orderId) });
        Log.d("deleteOrder", "orderId: " + orderId);
        db.close();
    }

    public ArrayList<Order> getAllComingOrders(int userId) {
        ArrayList<Order> orderArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ TABLE_ORDERS + " WHERE " + KEY_PAID + " = 1 AND " + FOREIGN_KEY_REF_USER + " = " + userId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Order order = new Order();
            order.setId(cursor.getInt(0));
            order.setUserId(cursor.getInt(1));
            order.setRestaurantId(cursor.getInt(2));
            order.setPaid(cursor.getInt(3));
            order.setUserName(cursor.getString(4));
            order.setPhone(cursor.getString(5));
            order.setAddress(cursor.getString(6));
            order.setCreatedAt(cursor.getString(7));

            orderArrayList.add(order);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return orderArrayList;
    }

    public ArrayList<Order> getAllDraftOrders(int userId) {
        ArrayList<Order> orderArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ TABLE_ORDERS + " WHERE " + KEY_PAID + " = 0 AND " + FOREIGN_KEY_REF_USER + " = " + userId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Order order = new Order();
            order.setId(cursor.getInt(0));
            order.setUserId(cursor.getInt(1));
            order.setRestaurantId(cursor.getInt(2));
            order.setPaid(cursor.getInt(3));
            order.setUserName(cursor.getString(4));
            order.setPhone(cursor.getString(5));
            order.setAddress(cursor.getString(6));
            order.setCreatedAt(cursor.getString(7));

            orderArrayList.add(order);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return orderArrayList;
    }

    public ArrayList<Order> getAllPaidOrders(int userId) {
        ArrayList<Order> orderArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ TABLE_ORDERS + " WHERE " + KEY_PAID + " > 0 AND " + FOREIGN_KEY_REF_USER + " = " + userId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Order order = new Order();
            order.setId(cursor.getInt(0));
            order.setUserId(cursor.getInt(1));
            order.setRestaurantId(cursor.getInt(2));
            order.setPaid(cursor.getInt(3));
            order.setUserName(cursor.getString(4));
            order.setPhone(cursor.getString(5));
            order.setAddress(cursor.getString(6));
            order.setCreatedAt(cursor.getString(7));

            orderArrayList.add(order);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return orderArrayList;
    }

    public Order getOrderById(long orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ORDERS, null, KEY_ID + " = ?", new String[] { String.valueOf(orderId)}, null, null, null);
        Order order = null;

        if (cursor.moveToFirst()) {
            order = new Order();
            order.setId(cursor.getInt(0));
            order.setUserId(cursor.getInt(1));
            order.setRestaurantId(cursor.getInt(2));
            order.setPaid(cursor.getInt(3));
            order.setUserName(cursor.getString(4));
            order.setPhone(cursor.getString(5));
            order.setAddress(cursor.getString(6));
            order.setCreatedAt(cursor.getString(7));
        }
        cursor.close();
        db.close();
        return order;
    }

    public void setPaymentOrder(int status, String name, String phone, String address, int orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "UPDATE " + TABLE_ORDERS +
                " SET " + KEY_PAID + " = " + status + ", " +
                KEY_NAME + " = '" + name + "', " +
                KEY_PHONE + " = '" + phone + "', " +
                KEY_ADDRESS + " = '" + address + "' " +
                " WHERE " + KEY_ID + " = " + orderId;
        db.execSQL(selectQuery);
        db.close();
    }

    //SQl for Order_Detail
    public OrderDetail addOrderDetail(OrderDetail orderDetail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FOREIGN_KEY_REF_ORDER, orderDetail.getOrderId());
        values.put(FOREIGN_KEY_REF_FOOD, orderDetail.getFood().getFoodId());
        values.put(KEY_PRICE, orderDetail.getFood().getPrice());
        long rowInserted = db.insert(TABLE_ORDER_DETAILS, null, values);

        if (rowInserted != - 1) {
            orderDetail = this.getOrderDetailById(rowInserted);
        } else {
            Toast.makeText(context, "Đã xảy ra sự cố trong quá trình thêm 'Chi tiết đơn hàng'", Toast.LENGTH_LONG).show();
        }
        db.close();
        return orderDetail;
    }

    public void deleteOrderDetail(OrderDetail orderDetail) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDER_DETAILS, KEY_ID + " = ?", new String[] { String.valueOf(orderDetail.getId())});
        db.close();
        handleIfEmptyOrder(orderDetail.getOrderId());
    }

    public HashMap<Integer, OrderDetail> getAllOrderDetailsInOrder(int orderId) {
        HashMap<Integer, OrderDetail> orderDetailHashMap = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " +TABLE_ORDER_DETAILS + " WHERE " + FOREIGN_KEY_REF_ORDER + " = ? ORDER BY " + KEY_CREATED_AT + " ASC";
        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(orderId)});
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(cursor.getInt(0));
            orderDetail.setOrderId(cursor.getInt(1));
            orderDetail.setQuantity(cursor.getInt(3));
            orderDetail.setPrice(cursor.getInt(4));
            orderDetail.setCreatedAt(cursor.getString(5));
            orderDetail.setUpdatedAt(cursor.getString(6));
            Food food;
            food = this.getFoodById(cursor.getInt(2));

            orderDetailHashMap.put(food.getFoodId(), orderDetail);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return orderDetailHashMap;
    }

    public OrderDetail getOrderDetailById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ORDER_DETAILS, null, KEY_ID + " = ?", new String[] { String.valueOf(id)}, null, null, null);
        OrderDetail orderDetail = null;

        if (cursor.moveToFirst()) {
            orderDetail = new OrderDetail();
            Food food;
            food = this.getFoodById(cursor.getInt(2));
            orderDetail.setId(cursor.getInt(0));
            orderDetail.setOrderId(cursor.getInt(1));
            orderDetail.setFood(food);
            orderDetail.setQuantity(cursor.getInt(3));
            orderDetail.setPrice(cursor.getInt(4));
            orderDetail.setCreatedAt(cursor.getString(5));
            orderDetail.setUpdatedAt(cursor.getString(6));
        }
        cursor.close();
        db.close();
        return orderDetail;
    }

    public ArrayList<OrderDetail> getOrderDetailByOrderID(int id) {
        ArrayList<OrderDetail> orderDetailArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ TABLE_ORDER_DETAILS + " WHERE " + FOREIGN_KEY_REF_ORDER + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            OrderDetail orderDetail = new OrderDetail();
            Food food;
            food = getFoodById(cursor.getInt(2));
            orderDetail.setId(cursor.getInt(0));
            orderDetail.setOrderId(cursor.getInt(1));
            orderDetail.setFood(food);
            orderDetail.setQuantity(cursor.getInt(3));
            orderDetail.setPrice(cursor.getInt(4));
            orderDetail.setCreatedAt(cursor.getString(5));
            orderDetail.setUpdatedAt(cursor.getString(6));

            orderDetailArrayList.add(orderDetail);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return orderDetailArrayList;
    }

    public void handleIfEmptyOrder(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        long orderDetailsCount = DatabaseUtils.longForQuery(db, "SELECT COUNT (*) FROM " + TABLE_ORDER_DETAILS + " WHERE " + FOREIGN_KEY_REF_ORDER + " = ?", new String[] { String.valueOf(orderId) });

        if (orderDetailsCount <= 0) {
            deleteOrder(orderId);
        }
        db.close();
    }

    public void updateOrderDetail(OrderDetail orderDetail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FOREIGN_KEY_REF_ORDER, orderDetail.getOrderId());
        values.put(FOREIGN_KEY_REF_FOOD, orderDetail.getFood().getFoodId());
        values.put(KEY_QUANTITY, orderDetail.getQuantity());
        values.put(KEY_PRICE, orderDetail.getPrice());
        values.put(KEY_CREATED_AT, orderDetail.getCreatedAt());
        values.put(KEY_UPDATED_AT, getDateTime());

        db.update(TABLE_ORDER_DETAILS, values, KEY_ID + " = ?", new String[] { String.valueOf(orderDetail.getId()) });
        db.close();
    }

    //SQl for Restaurants
    public ArrayList<Restaurant> getAllRestaurants() {
        ArrayList<Restaurant> restaurantArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ TABLE_RESTAURANTS + " ORDER BY " + KEY_ID + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Restaurant restaurant = new Restaurant();
            restaurant.setId(cursor.getInt(0));
            restaurant.setName(cursor.getString(1));
            restaurant.setAddress(cursor.getString(2));
            restaurant.setVote(cursor.getInt(3));
            restaurant.setCoverImage(cursor.getString(4));
            restaurant.setThumbImage(cursor.getString(5));

            restaurantArrayList.add(restaurant);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return restaurantArrayList;
    }

    public ArrayList<Restaurant> getAllRestaurantsByName(String search) {
        ArrayList<Restaurant> restaurantArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ TABLE_RESTAURANTS + " WHERE " + KEY_NAME + " LIKE '%" + search + "%' ORDER BY " + KEY_ID + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Restaurant restaurant = new Restaurant();
            restaurant.setId(cursor.getInt(0));
            restaurant.setName(cursor.getString(1));
            restaurant.setAddress(cursor.getString(2));
            restaurant.setVote(cursor.getInt(3));
            restaurant.setCoverImage(cursor.getString(4));
            restaurant.setThumbImage(cursor.getString(5));

            restaurantArrayList.add(restaurant);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return restaurantArrayList;
    }

    public Restaurant getRestaurant(int restaurantId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RESTAURANTS, null, KEY_ID + " = ?", new String[] { String.valueOf(restaurantId)}, null, null, null);
        Restaurant restaurant = null;

        if (cursor.moveToFirst()) {
            restaurant = new Restaurant();
            restaurant.setId(cursor.getInt(0));
            restaurant.setName(cursor.getString(1));
            restaurant.setAddress(cursor.getString(2));
            restaurant.setVote(cursor.getInt(3));
            restaurant.setCoverImage(cursor.getString(4));
            restaurant.setThumbImage(cursor.getString(5));
        }
        cursor.close();
        db.close();
        return restaurant;
    }

    //SQl for Restaurants
    public Boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME,     user.getUserName());
        contentValues.put(KEY_PHONE,    user.getPhone());
        contentValues.put(KEY_ADDRESS,  user.getAddress());
        contentValues.put(KEY_EMAIL,    user.getEmail());
        contentValues.put(KEY_PASSWORD, user.getPassword());
        long result = db.insert(TABLE_USERS, null, contentValues);

        if (result == -1) {
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public void changePassword(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "UPDATE " + TABLE_USERS +
                " SET " + KEY_PASSWORD + " = '" + user.getPassword() +
                "' WHERE " + KEY_EMAIL + " = '" + user.getEmail() + "'";
        db.execSQL(selectQuery);
        db.close();
    }

    public Boolean checkExistUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {email});

        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public User getInfoUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {email});
        User user = new User();

        if (cursor.moveToFirst()) {
            user.setUserId(cursor.getInt(0));
            user.setUserName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setAddress(cursor.getString(4));
            user.setPhone(cursor.getString(5));
        }
        cursor.close();
        db.close();
        return user;
    }

    public Boolean loginUser(String email, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_EMAIL + " = ? AND " + KEY_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {email, pass});

        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public void updateInfoUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "UPDATE " + TABLE_USERS +
                " SET " + KEY_NAME + " = '" + user.getUserName() + "', " +
                KEY_PHONE + " = '" + user.getPhone() + "', " +
                KEY_ADDRESS + " = '" + user.getAddress() +
                "' WHERE " + KEY_EMAIL + " = '" + user.getEmail() + "'";
        db.execSQL(selectQuery);
        db.close();
    }
}