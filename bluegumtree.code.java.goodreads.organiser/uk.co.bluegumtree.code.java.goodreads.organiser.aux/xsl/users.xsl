<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:atom="http://www.w3.org/2005/Atom"
    xmlns:dc="http://purl.org/dc/terms"
    xmlns:doc = "http://xml.code.bluegumtree.co.uk/2011/Documentation"
    exclude-result-prefixes="xs"
    version="2.0"
    xml:base="xsl">

    <xsl:param name="dataRoot" select="''" as="xs:string" />
    <xsl:param name="saveLocation" select="''" as="xs:string" />

    <xsl:include href="formatting.xsl" />

    <xsl:output 
        name="review"
        encoding="UTF-8"
        media-type="text/xml"
        method="xml"
        indent="yes"
        omit-xml-declaration="yes"
    />
    
    <xsl:template match="/">        
        <xsl:apply-templates select="GoodreadsResponse" />
    </xsl:template>
    
    <xsl:template match="GoodreadsResponse">
        <xsl:apply-templates select="user" mode="file" /> 
    </xsl:template>
        
    <xsl:template match="user" mode="file">        
        <xsl:variable name="userId" select="id" />
        
        <xsl:if test="$userId != ''">
            
            <!-- Build filename -->
            <xsl:variable name="filename" select="concat($saveLocation, 'users/', $userId, '.xml')" />
            
            <!-- Build the new structure and populate with required data -->
            <xsl:variable name="user" as="document-node()">
                <xsl:document>
                    <xsl:copy-of select="."/>      
                </xsl:document>
            </xsl:variable>
            
            <!-- Normalise space to counter wierdness introduced by use of CDATA elements -->
            <xsl:variable name="outgoingXml" as="document-node()">
                <xsl:document>
                    <xsl:apply-templates select="$user" mode="format" />
                </xsl:document>
            </xsl:variable>
            
            <!-- Output normalised result -->
            <xsl:result-document href="{$filename}" format="review" validation="strip">
                <xsl:copy-of select="$outgoingXml" />
            </xsl:result-document>
            
        </xsl:if>       
    </xsl:template>
           
</xsl:stylesheet>