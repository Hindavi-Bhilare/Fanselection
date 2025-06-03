// Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 09-Nov-12 6:48:02 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DXFImageHandler.java

package org.kabeja.parser.entities;

import org.kabeja.dxf.DXFEntity;

import org.kabeja.dxf.DXFImage;
import org.kabeja.math.Point;
import org.kabeja.parser.DXFValue;

// Referenced classes of package org.kabeja.parser.entities:
//            AbstractEntityHandler

public class DXFImageHandler extends AbstractEntityHandler
{

    public DXFImageHandler()
    {
    }

    public String getDXFEntityName()
    {
        return "IMAGE";
    }

    public void endDXFEntity()
    {
    }

    public DXFEntity getDXFEntity()
    {
        return image;
    }

    public boolean isFollowSequence()
    {
        return false;
    }

    public void parseGroup(int i, DXFValue dxfvalue)
    {
        switch(i)
        {
        case 10: // '\n'
            image.getInsertPoint().setX(dxfvalue.getDoubleValue());
            break;

        case 20: // '\024'
            image.getInsertPoint().setY(dxfvalue.getDoubleValue());
            break;

        case 30: // '\036'
            image.getInsertPoint().setZ(dxfvalue.getDoubleValue());
            break;

        case 340: 
            image.setImageDefObjectID(dxfvalue.getValue());
            break;

        case 11: // '\013'
            image.getVectorU().setX(dxfvalue.getDoubleValue());
            if(dxfvalue.getDoubleValue()!=0.0 && image.getImageSizeAlongU()!=0.0)
            	image.setImageSizeAlongU(dxfvalue.getDoubleValue()*image.getVectorU().getX());
            break;

        case 21: // '\025'
            image.getVectorU().setY(dxfvalue.getDoubleValue());
            if(dxfvalue.getDoubleValue()!=0.0 && image.getImageSizeAlongU()!=0.0)
            	image.setImageSizeAlongU(dxfvalue.getDoubleValue()*image.getVectorU().getY());
            break;

        case 31: // '\037'
            image.getVectorU().setZ(dxfvalue.getDoubleValue());
            break;

        case 12: // '\f'
            image.getVectorV().setX(dxfvalue.getDoubleValue());
            if(dxfvalue.getDoubleValue()!=0.0 && image.getImageSizeAlongV()!=0.0)
            	image.setImageSizeAlongV(dxfvalue.getDoubleValue()*image.getVectorV().getX());
            break;

        case 22: // '\026'
            image.getVectorV().setY(dxfvalue.getDoubleValue());
            if(dxfvalue.getDoubleValue()!=0.0 && image.getImageSizeAlongV()!=0.0)
            	image.setImageSizeAlongV(dxfvalue.getDoubleValue()*image.getVectorV().getY());
            break;

        case 32: // ' '
            image.getVectorV().setZ(dxfvalue.getDoubleValue());
            break;

        case 13: // '\r'
        	if(image.getVectorU().getX()!=0.0)
        		image.setImageSizeAlongU(dxfvalue.getDoubleValue()*image.getVectorU().getX());
        	else if(image.getVectorU().getY()!=0.0)
        		image.setImageSizeAlongU(dxfvalue.getDoubleValue()*image.getVectorU().getY());
        	else
        		image.setImageSizeAlongU(dxfvalue.getDoubleValue());
            break;

        case 23: // '\027'
        	if(image.getVectorV().getY()!=0.0)
        		image.setImageSizeAlongV(dxfvalue.getDoubleValue()*image.getVectorV().getY());
        	else if(image.getVectorV().getX()!=0.0)
        		image.setImageSizeAlongV(dxfvalue.getDoubleValue()*image.getVectorV().getX());
        	else
        		image.setImageSizeAlongV(dxfvalue.getDoubleValue());
            break;

        case 280: 
            image.setClipping(dxfvalue.getBooleanValue());
            break;

        case 14: // '\016'
            clippingPoint = new Point();
            clippingPoint.setX(dxfvalue.getDoubleValue());
            image.addClippingPoint(clippingPoint);
            break;

        case 24: // '\030'
            clippingPoint.setY(dxfvalue.getDoubleValue());
            break;

        case 281: 
            image.setBrightness(dxfvalue.getDoubleValue());
            break;

        case 282: 
            image.setContrast(dxfvalue.getDoubleValue());
            break;

        case 283: 
            image.setFade(dxfvalue.getDoubleValue());
            break;

        case 71: // 'G'
            if(dxfvalue.getIntegerValue() == 1)
            {
                image.setRectangularClipping(true);
                break;
            }
            if(dxfvalue.getIntegerValue() == 2)
                image.setPolygonalClipping(true);
            break;

        default:
            super.parseCommonProperty(i, dxfvalue, image);
            break;
        }
    }

    public void startDXFEntity()
    {
        image = new DXFImage();
        image.setDXFDocument(doc);
    }

    public static final int GROUPCODE_IMAGEDEF_HARDREFERENCE = 340;
    public static final int GROUPCODE_VECTOR_U_X = 11;
    public static final int GROUPCODE_VECTOR_U_Y = 21;
    public static final int GROUPCODE_VECTOR_U_Z = 31;
    public static final int GROUPCODE_VECTOR_V_X = 12;
    public static final int GROUPCODE_VECTOR_V_Y = 22;
    public static final int GROUPCODE_VECTOR_V_Z = 32;
    public static final int GROUPCODE_IAMGESIZE_U = 13;
    public static final int GROUPCODE_IAMGESIZE_V = 23;
    public static final int GROUPCODE_DISPLAY_PROPERTY = 70;
    public static final int GROUPCODE_BRIGHTNESS = 281;
    public static final int GROUPCODE_CONTRAST = 282;
    public static final int GROUPCODE_FADE = 283;
    public static final int GROUPCODE_NUMBER_CLIP_BOUNDARY = 91;
    public static final int GROUPCODE_CLIP_BOUNDARY_X = 14;
    public static final int GROUPCODE_CLIP_BOUNDARY_Y = 24;
    public static final int GROUPCODE_CLIP_BOUNDARY_TYPE = 71;
    public static final int GROUPCODE_CLIPPING_STATE = 280;
    protected DXFImage image;
    protected Point clippingPoint;
}
