package ulben;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.ParseException;

public class NumericTextField extends JTextField implements NumericPlainDocument.InsertErrorListener {

    int step = 1;

    public NumericTextField() {
        this(null, 0, null, 1);
    }

    public NumericTextField(String text, int columns, DecimalFormat format, int step) {
        super(null, text, columns);

        NumericPlainDocument numericDoc = (NumericPlainDocument) getDocument();
        if (format != null) {
            numericDoc.setFormat(format);
        }

        numericDoc.addInsertErrorListener(this);

        this.step = step;

        this.addFocusListener((new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                try {
                    if (UlbenUtils.readSetting("ROUND") != null)
                    try {
                        int textI = Integer.parseInt(getText());
                        long newVal = step * (Math.round((double)textI / step));
                        getDocument().remove(0, getDocument().getLength());
                        getDocument().insertString(0, String.valueOf(newVal), null);
                    } catch (BadLocationException badLocationException) {
                        badLocationException.printStackTrace();
                    }
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        }));

        this.addKeyListener((new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 107 || e.getKeyCode() == KeyEvent.VK_UP) {
                    try {
                        int newVal = Integer.parseInt(getText()) + step;
                        getDocument().remove(0, getDocument().getLength());
                        getDocument().insertString(0, String.valueOf(newVal), null);
                    } catch (BadLocationException badLocationException) {
                        badLocationException.printStackTrace();
                    }
                }
                if (e.getKeyCode() == 109 || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    try {
                        int newVal = Integer.parseInt(getText()) - step;
                        getDocument().remove(0, getDocument().getLength());
                        getDocument().insertString(0, String.valueOf(newVal), null);
                    } catch (BadLocationException badLocationException) {
                        badLocationException.printStackTrace();
                    }
                }
            }
        }));
    }


    public NumericTextField(int step) {
        this(null, 0, null, step);
    }

    public NumericTextField(int columns, DecimalFormat format) {
        this(null, columns, format, 1);
    }

    public NumericTextField(String text) {
        this(text, 0, null, 1);
    }

    public NumericTextField(String text, int columns) {
        this(text, columns, null, 1);
    }

    public void setFormat(DecimalFormat format) {
        ((NumericPlainDocument) getDocument()).setFormat(format);
    }

    public DecimalFormat getFormat() {
        return ((NumericPlainDocument) getDocument()).getFormat();
    }

    public void formatChanged() {
        setFormat(getFormat());
    }

    public Long getLongValue() throws ParseException {
        return ((NumericPlainDocument) getDocument()).getLongValue();
    }

    public Double getDoubleValue() throws ParseException {
        return ((NumericPlainDocument) getDocument()).getDoubleValue();
    }

    public Number getNumberValue() throws ParseException {
        return ((NumericPlainDocument) getDocument()).getNumberValue();
    }

    public void setValue(Number number) {
        setText(getFormat().format(number));
    }

    public void setValue(long l) {
        setText(getFormat().format(l));
    }

    public void setValue(double d) {
        setText(getFormat().format(d));
    }

    public void normalize() throws ParseException {
        setText(getFormat().format(getNumberValue()));
    }

    public void insertFailed(NumericPlainDocument doc, int offset, String str, AttributeSet a) {
    }

    protected Document createDefaultModel() {
        return new NumericPlainDocument();
    }
}
