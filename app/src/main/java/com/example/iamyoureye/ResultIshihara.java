package com.example.iamyoureye;

public class ResultIshihara {
    int img;
    String answer;
    String explain;

    public ResultIshihara(int img, String answer, String explain) {
        this.img = img;
        this.answer = answer;
        this.explain = explain;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
