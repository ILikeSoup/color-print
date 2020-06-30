package cn.soup.colorprint;

/**
 * 颜色
 */
public enum Color {
    BLACK(30),
    RED(31),
    GREEN(32),
    YELLOW(33),
    BLUE(34),
    PINK(35),
    CYAN(36),
    GRAY(37),
    // 亮色，没有对应的背景色，取其暗色的背景色
    L_RED(91, 41),
    L_GREEN(92, 42),
    L_YELLOW(93, 43),
    L_BLUE(94, 44),
    L_PINK(95, 45),
    L_CYAN(96, 46),
    L_GRAY(97, 47),
    DEFAULT(39);

    private int code;
    private int bgCode;

    private Color(int code) {
        this.code = code;
        this.bgCode = code + 10;
    }

    private Color(int code, int bgCode) {
        this.code = code;
        this.bgCode = bgCode;
    }

    /**
     * 彩虹，黑白不分
     * @return
     */
    public static Color[] rainbow() {
        Color[] r = new Color[values().length - 2];
        System.arraycopy(values(), 1, r, 0, values().length - 2);
        return r;
    }

    public int getCode() {
        return code;
    }

    public int getBgCode() {
        return bgCode;
    }

}
