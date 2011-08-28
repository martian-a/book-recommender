<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:atom="http://www.w3.org/2005/Atom"
    xmlns:dc="http://purl.org/dc/terms"
    xmlns:doc = "http://xml.code.bluegumtree.co.uk/2011/Documentation"
    exclude-result-prefixes="xs"
    version="2.0"
    xml:base="xsl">
            
    <xsl:param name="focalBookId" select="''" as="xs:string" />
    <xsl:param name="dataRoot" select="''" as="xs:string" />
    <xsl:param name="saveLocation" select="''" as="xs:string" />

    <!-- xsl:include href="formatting.xsl" /-->
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
        <xsl:apply-templates select="search/results/work" />   
    </xsl:template>
    
    <xsl:template match="work">
        
        <!-- Determine the ID of this work -->
        <xsl:variable name="workId" select="id" />
                
        <xsl:if test="$focalBookId != '' and $workId != ''">
            
            <!-- Build filename -->
            <xsl:variable name="filename" select="concat($saveLocation, 'works/', $workId, '.xml')" />
            
            <xsl:variable name="incomingXml" as="document-node()">
                <xsl:document>
                    <xsl:copy-of select="."  />    
                </xsl:document>                
            </xsl:variable>
            
            <xsl:variable name="existingXml" select="document($filename)" as="document-node()?" />
            
            <xsl:variable name="book" as="document-node()">
                <xsl:document>
                    <combined>
                        <xsl:copy-of select="$existingXml/combined//work" />
                        <xsl:copy-of select="$incomingXml" />
                        <books>
                            <xsl:copy-of select="$existingXml/combined/books//book" />
                            <book id="{$focalBookId}" />
                        </books>
                    </combined>
                </xsl:document>
                
            </xsl:variable>
            
            <!-- Normalise space to counter wierdness introduced by use of CDATA elements -->
            <xsl:variable name="outgoingXml" as="document-node()">
                <xsl:document>
                    <xsl:apply-templates select="$book" mode="format" />
                </xsl:document>
            </xsl:variable>
            
            <!-- Output normalised result -->
            <xsl:result-document href="{$filename}" format="review" validation="strip">
                <xsl:copy-of select="$outgoingXml" />
            </xsl:result-document>
            
        </xsl:if>
        
    </xsl:template>
    
</xsl:stylesheet>