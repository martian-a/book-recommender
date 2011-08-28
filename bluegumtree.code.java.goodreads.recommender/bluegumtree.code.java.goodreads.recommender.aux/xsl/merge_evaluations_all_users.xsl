<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:atom="http://www.w3.org/2005/Atom"
    xmlns:dc="http://purl.org/dc/terms"
    exclude-result-prefixes="xs"
    version="2.0"
    xml:base="xsl">

    <xsl:param name="saveLocation" select="''" as="xs:string" />
            
    <xsl:output 
        name="review"
        encoding="UTF-8"
        media-type="text/xml"
        method="xml"
        indent="yes"
        omit-xml-declaration="yes"      
    />
    
    <xsl:template match="/">        
        
        <!-- Build filename -->
        <xsl:variable name="filename" select="$saveLocation" />          
            
        <xsl:variable name="users" as="document-node()">
            <xsl:document>
                <users>
                    <xsl:for-each select="directory/file">                        
                        
                        <xsl:variable name="userXml">
                            <xsl:copy-of select="document(@src)" />
                        </xsl:variable> 
                        
                        <xsl:apply-templates select="$userXml" mode="merge" />
                    </xsl:for-each>
                </users>
            </xsl:document>        
        </xsl:variable>               
            
        <xsl:result-document href="{$filename}" format="review" validation="strip">
            <xsl:copy-of select="$users" />
        </xsl:result-document>
        
    </xsl:template>

    <xsl:template match="*" mode="merge">
        <xsl:element name="{name()}">
            <xsl:copy-of select="@*" />
            <xsl:apply-templates mode="merge" />
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="text()" mode="merge">
        <xsl:value-of select="normalize-space(.)" />  
    </xsl:template>
    
</xsl:stylesheet>