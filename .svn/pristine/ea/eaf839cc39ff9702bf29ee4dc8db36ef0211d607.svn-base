// Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 20-Oct-13 7:19:57 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SVGUtils.java

package org.kabeja.svg;

import java.io.File;



import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Iterator;
import org.kabeja.dxf.DXFLineType;
import org.kabeja.dxf.helpers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.AttributesImpl;

public class SVGUtils
{

    public SVGUtils()
    {
    }

    public static void startElement(ContentHandler handler, String element, Attributes attr)
        throws SAXException
    {
        handler.startElement("http://www.w3.org/2000/svg", element, element, attr);
    }

    public static void endElement(ContentHandler handler, String element)
        throws SAXException
    {
        handler.endElement("http://www.w3.org/2000/svg", element, element);
    }

    public static void addAttribute(AttributesImpl attr, String name, String value)
    {
        int index = attr.getIndex(name);
        if(index > -1)
            attr.removeAttribute(index);
        attr.addAttribute("", name, name, "CDATA", value);
    }

    public static void characters(ContentHandler handler, String text)
        throws SAXException
    {
        char data[] = text.toCharArray();
        handler.characters(data, 0, data.length);
    }

    public static void emptyElement(ContentHandler handler, String element, Attributes attr)
        throws SAXException
    {
        startElement(handler, element, attr);
        endElement(handler, element);
    }

    public static void addStrokeDashArrayAttribute(AttributesImpl attr, DXFLineType ltype)
    {
        addStrokeDashArrayAttribute(attr, ltype, 1.0D);
    }

    public static void addStrokeDashArrayAttribute(AttributesImpl attr, DXFLineType ltype, double scale)
    {
        if(ltype != null)
        {
            double pattern[] = ltype.getPattern();
            if(pattern.length > 0)
            {
                StringBuffer buf = new StringBuffer();
                for(int i = 0; i < pattern.length; i++)
                {
                    if(pattern[i] != 0.0D)
                        buf.append(format.format(Math.abs(pattern[i] * scale)));
                    else
                        buf.append("0.05%");
                    buf.append(", ");
                }

                buf.deleteCharAt(buf.length() - 2);
                addAttribute(attr, "stroke-dasharray", buf.toString());
            }
        }
    }

    public static String validateID(String id)
    {
        if(id.length() > 0)
        {
            StringBuffer buf = new StringBuffer();
            char first = id.charAt(0);
            if(!Character.isLetter(first) && first != ':')
                buf.append("ID");
            for(int i = 0; i < id.length(); i++)
            {
                char c = id.charAt(i);
                if(Character.isLetter(c) || Character.isDigit(c) || c == '-' || c == '.' || c == ':')
                {
                    buf.append(c);
                } else
                {
                    buf.append('_');
                    buf.append(c);
                    buf.append('_');
                }
            }

            return buf.toString();
        } else
        {
            return id;
        }
    }

    public static String reverseID(String id)
    {
        if(id.length() > 0)
        {
            StringBuffer buf = new StringBuffer();
            boolean marker = false;
            StringBuffer number = new StringBuffer();
            for(int i = 0; i < id.length(); i++)
            {
                char c = id.charAt(i);
                if(c == '_')
                {
                    if(marker)
                    {
                        if(number.length() > 0)
                        {
                            int x = Integer.parseInt(number.toString());
                            buf.append((char)x);
                            number.delete(0, number.length());
                        }
                        marker = false;
                    } else
                    {
                        marker = true;
                    }
                    continue;
                }
                if(marker)
                {
                    if(Character.isDigit(c))
                        number.append(c);
                } else
                {
                    buf.append(c);
                }
            }

            if(buf.toString().startsWith("ID"))
                buf.delete(0, "ID".length());
            return buf.toString();
        } else
        {
            return id;
        }
    }

    public static void textDocumentToSAX(ContentHandler handler, TextDocument doc)
        throws SAXException
    {
        StyledTextParagraph para;
        for(Iterator i = doc.getStyledParagraphIterator(); i.hasNext(); styledTextToSAX(handler, para))
            para = (StyledTextParagraph)i.next();

    }

    public static void styledTextToSAX(ContentHandler handler, StyledTextParagraph para)
        throws SAXException
    {
        AttributesImpl atts = new AttributesImpl();
        String decoration = "";
        if(para.isUnderline())
            decoration = decoration + "underline ";
        if(para.isOverline())
            decoration = decoration + "overline ";
        if(decoration.length() > 0)
            addAttribute(atts, "text-decoration", decoration);
        if(para.getLineIndex() > 0)
            addAttribute(atts, "dy", "1.3em");
        if(para.isNewline())
        {
            addAttribute(atts, "x", "" + para.getInsertPoint().getX());
            para.setNewline(false);
        }
        if(para.getValign() == 2)
            addAttribute(atts, "baseline-shift", "-100%");
        else
        if(para.getValign() == 0)
            addAttribute(atts, "baseline-shift", "sub");
        else
        if(para.getValign() == 4)
            addAttribute(atts, "baseline-shift", "-40%");
        if(para.getWidth() > 0.0D)
            addAttribute(atts, "textLength", "" + para.getWidth());
        if(para.isBold())
            addAttribute(atts, "font-weight", "bold");
        if(para.isItalic())
            addAttribute(atts, "font-style", "italic");
        if(para.getFont().length() > 0)
            addAttribute(atts, "font-family", para.getFont());
        if(para.getFontHeight() > 0.0D)
            addAttribute(atts, "font-size", "" + formatNumberAttribute(para.getFontHeight()*1.35));
        atts.addAttribute("http://www.w3.org/XML/1998/namespace", "space", "xml:space", "CDATA", "preserve");
        startElement(handler, "tspan", atts);
        characters(handler, para.getText());
        endElement(handler, "tspan");
    }

    public static String formatNumberAttribute(double v)
    {
        return format.format((float)v);
    }

    public static String fileToURI(File file)
    {
        StringBuffer buf = new StringBuffer();
        try
        {
            buf.append("file://");
            char c[] = file.toURL().toExternalForm().toCharArray();
            for(int i = 5; i < c.length; i++)
                if(Character.isWhitespace(c[i]))
                    buf.append("%20");
                else
                    buf.append(c[i]);

        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        return buf.toString();
    }

    public static String pathToURI(String path)
    {
        StringBuffer buf = new StringBuffer();
        char c[] = path.toCharArray();
        if(c.length > 0)
        {
            buf.append("file://");
            if(c[0] != '/')
                buf.append('/');
            for(int i = 0; i < c.length; i++)
            {
                if(Character.isWhitespace(c[i]))
                {
                    buf.append("%20");
                    continue;
                }
                if(c[i] == '\\')
                    buf.append('/');
                else
                    buf.append(c[i]);
            }

        }
        return buf.toString();
    }

    public static String lineWeightToStrokeWidth(int lineWeight)
    {
        double w = (double)lineWeight / 100D;
        return "" + w + " mm";
    }

    public static String lineWidthToStrokeWidth(LineWidth lw)
    {
        switch(lw.getType())
        {
        case 0: // '\0'
            double w = lw.getValue() / 100D;
            return formatNumberAttribute(w) + " mm";

        case 2: // '\002'
            return formatNumberAttribute(lw.getValue());

        case 1: // '\001'
            return formatNumberAttribute(lw.getValue()) + "%";
        }
        return "0.02%";
    }

    public static final String DEFAUL_ATTRIBUTE_TYPE = "CDATA";
    public static final String DEFAULT_ID_NAME_PREFIX = "ID";
    public static final char DEFAULT_CONVERT_MARKER_CHAR = 95;
    private static DecimalFormat format;

    static 
    {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        format = new DecimalFormat("###.###################################", symbols);
    }
}
