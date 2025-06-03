// Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 09-Nov-12 7:52:04 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SVGImageGenerator.java

package org.kabeja.svg.generators;

import java.util.Map;


import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFImage;
import org.kabeja.dxf.objects.DXFImageDefObject;
import org.kabeja.math.TransformContext;
import org.kabeja.svg.SVGUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

// Referenced classes of package org.kabeja.svg.generators:
//            AbstractSVGSAXGenerator

public class SVGImageGenerator extends AbstractSVGSAXGenerator
{

    public SVGImageGenerator()
    {
    }

    public void toSAX(ContentHandler handler, Map svgContext, DXFEntity entity, TransformContext transformContext)
        throws SAXException
    {
        DXFImage image = (DXFImage)entity;
        DXFImageDefObject imageDef = (DXFImageDefObject)image.getDXFDocument().getDXFObjectByID(image.getImageDefObjectID());
        if(imageDef != null)
        {
            AttributesImpl attr = new AttributesImpl();
            super.setCommonAttributes(attr, svgContext, image);
            if(image.getImageSizeAlongU()<0)
            {
            	 SVGUtils.addAttribute(attr, "x", SVGUtils.formatNumberAttribute(image.getInsertPoint().getX()+Math.abs(image.getImageSizeAlongV())));
            	 SVGUtils.addAttribute(attr, "y", SVGUtils.formatNumberAttribute((-image.getInsertPoint().getY())-2*Math.abs(image.getImageSizeAlongV())));
            }
            else
            {
            	SVGUtils.addAttribute(attr, "x", SVGUtils.formatNumberAttribute(image.getInsertPoint().getX()));
                SVGUtils.addAttribute(attr, "y", SVGUtils.formatNumberAttribute(-image.getInsertPoint().getY()-image.getImageSizeAlongV()));
            }
            SVGUtils.addAttribute(attr, "width", SVGUtils.formatNumberAttribute(Math.abs(image.getImageSizeAlongU())));
            SVGUtils.addAttribute(attr, "height", SVGUtils.formatNumberAttribute(Math.abs(image.getImageSizeAlongV())));
            if(image.getImageSizeAlongU()>0)
            	SVGUtils.addAttribute(attr, "transform","scale(1,-1)");
            else
            	SVGUtils.addAttribute(attr, "transform","scale(1,-1) rotate(90,"+image.getInsertPoint().getX()+","+(-image.getInsertPoint().getY()-image.getImageSizeAlongV())+") ");
            attr.addAttribute("http://www.w3.org/2000/xmlns/", "xlink", "xmlns:xlink", "CDATA", "http://www.w3.org/1999/xlink");
            attr.addAttribute("http://www.w3.org/1999/xlink", "href", "xlink:href", "CDATA", SVGUtils.pathToURI(imageDef.getFilename()));
            
            SVGUtils.emptyElement(handler, "image", attr);
        }
    }
}
