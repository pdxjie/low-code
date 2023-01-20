package com.pdx.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @Author: 派大星
 * @Date: 2023/01/19 2023/1/19
 * @Description:
 */
public class AvatarUtils {

    public static ArrayList<String> avatars = new ArrayList<>();

    static {
        avatars.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRe-JAvQ1bMyZE0e5JVFVhqUswtSGnyWL33-Q&usqp=CAU");
        avatars.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdPstCVI67fyAAUg3_MmHbeekc_VYneSP7mg&usqp=CAU");
        avatars.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQFen62js0Z6Solzw0z9_LgjiGlnq2cH4DiQg&usqp=CAU");
        avatars.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS57de2No_0ZTb6834A87hEy3UwwWZF2i1_2Q&usqp=CAU");
        avatars.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRstoUPhE7g5j3NcPLhXizNU802fPcbvX2COg&usqp=CAU");
        avatars.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHodtWxlQEj1M9eE4WbvJPt15Fr56exnWI6A&usqp=CAU");
        avatars.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7Zp3KENMmad9kT6m7HorQ4QeaX-NsRrPcdA&usqp=CAU");
        avatars.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7zbjSnSo3rERLFEfTkOhDtYFLjXp8hg5yJw&usqp=CAU");
        avatars.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQz3_FLGXahKII9UDgYPvo9irhGN_ORt9ej5Q&usqp=CAU");
        avatars.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9sen8__HpI_fSmdzP0VtHEJbUaI5LdWhldQ&usqp=CAU");
        avatars.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHJcAMz4OPfBi6D9jtcqmNxy249j4IQoViUA&usqp=CAU0");
        avatars.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSt45v20IhqwayXK2GZtWi9DxSgvG17kUK31g&usqp=CAU");
        avatars.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSo1bi4qjaarYpyWAGmFfOZWXM_I2QEoui33g&usqp=CAU");
        avatars.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRgwt2Lsrnrc_IW700bj5MmoTYkbbyNwUV93w&usqp=CAU");
    }

    public static String generatorAvatar(){
        Random random = new Random();
        int index = random.nextInt(13);
        return avatars.get(index);
    }

    public static void main(String[] args) {
        String s = AvatarUtils.generatorAvatar();
        System.out.println(s);
    }


}
