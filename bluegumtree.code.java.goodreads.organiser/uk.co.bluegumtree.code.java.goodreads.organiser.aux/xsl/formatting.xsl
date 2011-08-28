<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:atom="http://www.w3.org/2005/Atom"
    xmlns:dc="http://purl.org/dc/terms"
    xmlns:doc = "http://xml.code.bluegumtree.co.uk/2011/Documentation"
    exclude-result-prefixes="xs"    
    version="2.0"
   	xml:base="xsl">
    
    <doc:annotation>
        <doc:p>Matches any element, irrespective of it's name, and attempts to create a deep copy.</doc:p>
    </doc:annotation>
    <xsl:template match="*" mode="format">
        <xsl:element name="{name()}">
            <xsl:copy-of select="@*" />
            <xsl:apply-templates mode="format" />
        </xsl:element>
    </xsl:template>
    
    <doc:annotation>
        <doc:p>Matches any text node and copies it, normalising it in the process.</doc:p>
    </doc:annotation>
    <xsl:template match="text()" mode="format">
        <xsl:value-of select="normalize-space(.)" />  
    </xsl:template>
    
</xsl:stylesheet>