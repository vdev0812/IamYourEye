package com.example.iamyoureye;

import kotlin.jvm.internal.Intrinsics;

public class UserColor {
    String hex;
    String red;
    String green;
    String blue;

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        Intrinsics.checkNotNullParameter(hex, "hex");
        this.hex = hex;
    }

    public UserColor(String hex, String red, String green, String blue) {
        Intrinsics.checkNotNullParameter(hex, "hex");
        Intrinsics.checkNotNullParameter(red, "r");
        Intrinsics.checkNotNullParameter(green, "g");
        Intrinsics.checkNotNullParameter(blue, "b");
        this.hex = hex;
        this.green = green;
        this.red = red;
        this.blue = blue;
    }
}
