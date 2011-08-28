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
        <doc:p>The Goodreads ID of the user who wrote the reviews being processed.</doc:p>
    </doc:annotation>
    <xsl:param name="focalUserId" select="''" />
    
    <doc:annotation>
        <doc:p>The path to the root directory that additional data may be sourced from.</doc:p>
    </doc:annotation>
    <xsl:param name="dataRoot" select="''" as="xs:string" />
    
    <doc:annotation>
        <doc:p>The path to the Goodgle Books data directory for this user.</doc:p>
    </doc:annotation>
    <xsl:param name="dataRootGoogle" select="''" as="xs:string" />
    
    <doc:annotation>
        <doc:p>The path to the Open Library data directory for this user.</doc:p>
    </doc:annotation>
    <xsl:param name="dataRootOpenLibrary" select="''" as="xs:string" />
    
    <doc:annotation>
        <doc:p>The root directory that result-documents should be saved to.</doc:p>
    </doc:annotation>
    <xsl:param name="saveLocation" select="''" as="xs:string" />
    
    <doc:annotation>
        <doc:p>Loads the first page of Goodreads profile for the specified user into a variable, as a document node.</doc:p>
    </doc:annotation>
    <xsl:variable name="userProfileXml" select="document(concat($dataRoot, 'users/', $focalUserId, '_0.xml'))" as="document-node()" />
    
    <doc:annotation>
        <doc:p>Templates for making a deep copy of element.  Can be used to merge content from multiple DOM documents.</doc:p>
    </doc:annotation>
    <xsl:include href="formatting.xsl" />

    <xsl:output 
        name="review"
        encoding="UTF-8"
        media-type="text/xml"
        method="xml"
        indent="yes"
        omit-xml-declaration="yes"      
    />
    
    <doc:annotation>
        <doc:p>Matches and processes the root document element of the Goodreads XML file currently being processed.</doc:p>        
    </doc:annotation>
    <xsl:template match="/">        
        <xsl:apply-templates select="GoodreadsResponse" />
    </xsl:template>
    
    <doc:annotation>
        <doc:p>Matches the GoodreadsResponse element in the current file and initiates a loop through all the reviews contained within.</doc:p>
    </doc:annotation>
    <xsl:template match="GoodreadsResponse">        
        <xsl:apply-templates select="reviews" mode="file"/>   
    </xsl:template>
    
    <doc:annotation>
        <doc:p>Matches the reviews Element and sends it on for processing, along with the ID of the current user.</doc:p>
    </doc:annotation>
    <xsl:template match="reviews" mode="file">               
        <xsl:apply-templates select="review">
            <xsl:with-param name="userId" select="$userProfileXml/GoodreadsResponse/user/id" />
        </xsl:apply-templates>       
    </xsl:template>
    
    <doc:annotation>
        <doc:p>Matches each individual Goodreads review.  Determine's it's ID and popularity band.</doc:p>
        <doc:p>Initiates the merging of all the data sources.</doc:p>
        <doc:p>Post merge, uses the <dc:subject /> element copied from the Google Books XML, to checks whether the book is known to be fiction.  Otherwise is treated as Non-fiction.</doc:p>
        <doc:p>Outputs the newly filtered and restructured XML as a file.</doc:p>
    </doc:annotation>
    <xsl:template match="review">
        <xsl:param name="userId" />
        
        <!-- Determine the ID of this review -->
        <xsl:variable name="reviewId" select="id" />
        
        <!-- Determine the popularity band of this review -->
        <xsl:variable name="popularity">
            <xsl:variable name="rating" select="rating" as="xs:integer" />
            <xsl:choose>
                <xsl:when test="$rating &gt; 3">popular</xsl:when>
                <xsl:when test="$rating = 3">mediocre</xsl:when>
                <xsl:when test="$rating &lt; 1">unrated</xsl:when>
                <xsl:otherwise>unpopular</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        
        <xsl:if test="$userId != '' and $reviewId != '' and $popularity != ''">          
            
            <!-- Build the new structure and populate with required data -->
            <xsl:variable name="review" as="document-node()">
                <xsl:document>
                    <user>
                        <id><xsl:value-of select="$userId" /></id>
                        <xsl:apply-templates select="." mode="merge.goodreads" />
                    </user>       
                </xsl:document>
            </xsl:variable>
            
            <!-- Normalise space to counter wierdness introduced by use of CDATA elements -->
            <xsl:variable name="outgoingXml" as="document-node()">
                <xsl:document>
                    <xsl:apply-templates select="$review" mode="format" />
                </xsl:document>
            </xsl:variable>
            
            <xsl:variable name="isFiction" as="xs:integer" select="count($outgoingXml//book[@source = 'google']/dc:subject[contains(., 'Fiction') or contains(., 'fiction')][not(. = 'Non-fiction') and not(. = 'Non-Fiction')])" />
            
            <!-- Build filename -->
            <xsl:variable name="filename">
                <xsl:value-of select="concat($saveLocation, '/reviews/', $userId, '/')" />
                <xsl:if test="$isFiction &lt; 1">
                    <xsl:value-of select="'non-fiction/'" />
                </xsl:if>
                <xsl:value-of select="concat($popularity, '/', $reviewId, '.xml')" />                    
            </xsl:variable>    
            
            <!-- Output normalised result -->
            <xsl:result-document href="{$filename}" format="review" validation="strip">
                 <xsl:copy-of select="$outgoingXml" />
            </xsl:result-document>
            
        </xsl:if>
        
    </xsl:template>
        
    <doc:annotation>    
        <doc:p>Matches a Goodreads review element during the merge process.</doc:p>
    </doc:annotation>
    <xsl:template match="review" mode="merge.goodreads" priority="1">

        <xsl:element name="{name()}">
            <xsl:copy-of select="@*" />
            <xsl:apply-templates mode="merge" />
            <xsl:apply-templates select="self::review" mode="merge.google" />
            <xsl:apply-templates select="self::review" mode="merge.openlibrary" />
        </xsl:element>

    </xsl:template>
    
    <doc:annotation>
        <doc:p>Matches a Google Books review element during the merge process.  Makes a selective deep copy.</doc:p>
    </doc:annotation>
    <xsl:template match="review" mode="merge.google">
        <xsl:variable name="isbn" as="xs:string">
            <xsl:choose>
                <xsl:when test="descendant::book/isbn13">
                    <xsl:value-of select="descendant::book/isbn13" />
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="descendant::book/isbn10" />
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:if test="$isbn != '' and $dataRootGoogle != ''">
            <xsl:variable name="googleXml" select="document(concat($dataRootGoogle, '/books/', $isbn, '_0.xml'))" as="document-node()?" />            
            <xsl:if test="$googleXml//*[local-name(.) = 'entry']">
                <book source="google">
                    <xsl:apply-templates select="$googleXml//*[local-name(.) = 'entry']" mode="merge.google" />
                </book>  
            </xsl:if>
        </xsl:if>        
    </xsl:template>
    
    <doc:annotation>
        <doc:p>Matches the specified elements in the Google Books XML, irrespective of the namespace used. Makes a selective copy.</doc:p>
    </doc:annotation>
    <xsl:template match="*[local-name(.) = 'entry']" mode="merge.google">
        <xsl:copy-of select="*[local-name(.) = 'title']" />
        <xsl:copy-of select="*[local-name(.) = 'creator']" />
        <xsl:copy-of select="*[local-name(.) = 'language']" />
        <xsl:copy-of select="*[local-name(.) = 'subject']" />
    </xsl:template>
    
    <xsl:template match="response" mode="merge.openlibrary">
        <xsl:copy-of select="*/weight" />
        <xsl:copy-of select="*/number_of_pages" />
    </xsl:template>
 
    
    <xsl:template match="review" mode="merge.openlibrary">
        <xsl:variable name="isbn" as="xs:string">
            <xsl:choose>
                <xsl:when test="descendant::book/isbn13">
                    <xsl:value-of select="descendant::book/isbn13" />
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="descendant::book/isbn10" />
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:if test="$isbn != '' and $dataRootOpenLibrary != ''">            
            <xsl:variable name="openLibraryXml" select="document(concat($dataRootOpenLibrary, '/books/', $isbn, '_0.xml'))" as="document-node()?" />
            <xsl:if test="$openLibraryXml//response">
                <book source="openlibrary">                    
                    <xsl:apply-templates select="$openLibraryXml//response" mode="merge.openlibrary" />
                </book>
            </xsl:if>
        </xsl:if>
    </xsl:template>
           
    
    <xsl:template match="*" mode="merge">
        <xsl:apply-templates mode="merge" />        
    </xsl:template>
    
    <xsl:template match="text()" mode="merge" />
    
    <xsl:template match="review/id" mode="merge" priority="1">
        <xsl:copy-of select="." />
    </xsl:template>
    
    <xsl:template match="review/book | review/book/authors | review/shelves | review/book/authors/author" mode="merge" priority="1">
        <xsl:element name="{name()}">
            <xsl:copy-of select="@*" />
            <xsl:next-match />
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="review/book/id | review/book/isbn | review/book/isbn13 | review/book/title | review/book/num_pages | review/book/description | review/rating | review/book/published | review/shelves/shelf | review/book/authors/author/name | review/book/authors/author/id | review/book/language_code" mode="merge" priority="1">
        <xsl:element name="{name()}">
            <xsl:copy-of select="@*" />
            <xsl:value-of select="text()" />
        </xsl:element>
    </xsl:template>       
    
</xsl:stylesheet>
