package cn.soup.colorprint;

/**
 * 样式
 */
public enum Style {
    /**
     * 重置
     */
    DEFAULT(0),
    /**
     * 加粗
     */
    BOLD(1),
    /**
     * 斜体
     */
    ITALIC(3),
    /**
     * 下划线
     */
    UNDERLINE(4),
    /**
     * 字体颜色和背景色交换
     */
    REVERSAL(7),
    /**
     * 删除线
     */
    LINE_THROUGH(9),
    /**
     * 无特效
     */
    SKIP(10)
    ;
    private int code;

    private Style(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
