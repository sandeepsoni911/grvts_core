package com.owners.gravitas.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class SlackAttachment.
 *
 * @author vishwanathm
 */
public class SlackAttachment {

    /** The fallback. */
    private String fallback;

    /** The color. */
    private String color;

    /** The pretext. */
    private String pretext;

    /** The authorName. */
    private String authorName;

    /** The authorLink. */
    private String authorLink;

    /** The authorIcon. */
    private String authorIcon;

    /** The title. */
    private String title;

    /** The titleLink. */
    private String titleLink;

    /** The text. */
    private String text;

    /** The imageUrl. */
    private String imageUrl;

    /** The thumbUrl. */
    private String thumbUrl;

    /** The slackPostDateTime. */
    private long slackPostDateTime;

    /** The fields. */
    private List< Field > fields = new ArrayList<>();

    /**
     * The Class Field.
     */
    public class Field {

        /** The title. */
        private String title;

        /** The value. */
        private String value;

        /**
         * Gets the title.
         *
         * @return the title
         */
        public String getTitle() {
            return title;
        }

        /**
         * Sets the title.
         *
         * @param title
         *            the new title
         */
        public void setTitle( final String title ) {
            this.title = title;
        }

        /**
         * Gets the value.
         *
         * @return the value
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value.
         *
         * @param value
         *            the new value
         */
        public void setValue( final String value ) {
            this.value = value;
        }
    }

    /**
     * Gets the fallback.
     *
     * @return the fallback
     */
    public String getFallback() {
        return fallback;
    }

    /**
     * Sets the fallback.
     *
     * @param fallback
     *            the new fallback
     */
    public void setFallback( final String fallback ) {
        this.fallback = fallback;
    }

    /**
     * Gets the color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color.
     *
     * @param color
     *            the new color
     */
    public void setColor( final String color ) {
        this.color = color;
    }

    /**
     * Gets the pretext.
     *
     * @return the pretext
     */
    public String getPretext() {
        return pretext;
    }

    /**
     * Sets the pretext.
     *
     * @param pretext
     *            the new pretext
     */
    public void setPretext( final String pretext ) {
        this.pretext = pretext;
    }

    /**
     * Gets the authorName.
     *
     * @return the authorName
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * Sets the authorName.
     *
     * @param authorName
     *            the new authorName
     */
    @JsonProperty( "author_name" )
    public void setAuthorName( final String author_name ) {
        this.authorName = author_name;
    }

    /**
     * Gets the authorLink.
     *
     * @return the authorLink
     */
    public String getAuthorLink() {
        return authorLink;
    }

    /**
     * Sets the authorLink.
     *
     * @param authorLink
     *            the new authorLink
     */
    @JsonProperty( "author_link" )
    public void setAuthorLink( final String author_link ) {
        this.authorLink = author_link;
    }

    /**
     * Gets the authorIcon.
     *
     * @return the authorIcon
     */
    public String getAuthorIcon() {
        return authorIcon;
    }

    /**
     * Sets the authorIcon.
     *
     * @param authorIcon
     *            the new authorIcon
     */
    @JsonProperty( "author_icon" )
    public void setAuthorIcon( final String author_icon ) {
        this.authorIcon = author_icon;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title
     *            the new title
     */
    public void setTitle( final String title ) {
        this.title = title;
    }

    /**
     * Gets the titleLink.
     *
     * @return the titleLink
     */
    public String getTitleLink() {
        return titleLink;
    }

    /**
     * Sets the titleLink.
     *
     * @param titleLink
     *            the new titleLink
     */
    @JsonProperty( "title_link" )
    public void setTitleLink( final String title_link ) {
        this.titleLink = title_link;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text.
     *
     * @param text
     *            the new text
     */
    public void setText( final String text ) {
        this.text = text;
    }

    /**
     * Gets the imageUrl.
     *
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the imageUrl.
     *
     * @param imageUrl
     *            the new imageUrl
     */
    @JsonProperty( "image_url" )
    public void setImageUrl( final String image_url ) {
        this.imageUrl = image_url;
    }

    /**
     * Gets the thumbUrl.
     *
     * @return the thumbUrl
     */
    public String getThumbUrl() {
        return thumbUrl;
    }

    /**
     * Sets the thumbUrl.
     *
     * @param thumbUrl
     *            the new thumbUrl
     */
    @JsonProperty( "thumb_url" )
    public void setThumbUrl( final String thumb_url ) {
        this.thumbUrl = thumb_url;
    }

    /**
     * Gets the slackPostDateTime.
     *
     * @return the slackPostDateTime
     */
    public long getSlackPostDateTime() {
        return slackPostDateTime;
    }

    /**
     * Sets the slackPostDateTime.
     *
     * @param slackPostDateTime
     *            the new slackPostDateTime
     */
    @JsonProperty( "ts" )
    public void setSlackPostDateTime( final long ts ) {
        this.slackPostDateTime = ts;
    }

    /**
     * Gets the fields.
     *
     * @return the fields
     */
    public List< Field > getFields() {
        return fields;
    }

    /**
     * Sets the fields.
     *
     * @param fields
     *            the new fields
     */
    public void setFields( final List< Field > fields ) {
        this.fields = fields;
    }

    /**
     * Adds the field.
     *
     * @param field
     *            the field
     */
    public void addField( final Field field ) {
        this.fields.add( field );
    }
}
