package com.owners.gravitas.dto.request;

/**
 * The Class SlackPostData.
 *
 * @author shivamm
 */
public class SlackPostData {

    /** The token. */
    private String token;

    /** The team id. */
    private String team_id;

    /** The team domain. */
    private String team_domain;

    /** The channel id. */
    private String channel_id;

    /** The channel name. */
    private String channel_name;

    /** The user name. */
    private String user_name;

    /** The text. */
    private String text;

    /**
     * Gets the token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token.
     *
     * @param token
     *            the new token
     */
    public void setToken( String token ) {
        this.token = token;
    }

    /**
     * Gets the team id.
     *
     * @return the team id
     */
    public String getTeam_id() {
        return team_id;
    }

    /**
     * Sets the team id.
     *
     * @param team_id
     *            the new team id
     */
    public void setTeam_id( String team_id ) {
        this.team_id = team_id;
    }

    /**
     * Gets the team domain.
     *
     * @return the team domain
     */
    public String getTeam_domain() {
        return team_domain;
    }

    /**
     * Sets the team domain.
     *
     * @param team_domain
     *            the new team domain
     */
    public void setTeam_domain( String team_domain ) {
        this.team_domain = team_domain;
    }

    /**
     * Gets the channel id.
     *
     * @return the channel id
     */
    public String getChannel_id() {
        return channel_id;
    }

    /**
     * Sets the channel id.
     *
     * @param channel_id
     *            the new channel id
     */
    public void setChannel_id( String channel_id ) {
        this.channel_id = channel_id;
    }

    /**
     * Gets the channel name.
     *
     * @return the channel name
     */
    public String getChannel_name() {
        return channel_name;
    }

    /**
     * Sets the channel name.
     *
     * @param channel_name
     *            the new channel name
     */
    public void setChannel_name( String channel_name ) {
        this.channel_name = channel_name;
    }

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * Sets the user name.
     *
     * @param user_name
     *            the new user name
     */
    public void setUser_name( String user_name ) {
        this.user_name = user_name;
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
    public void setText( String text ) {
        this.text = text;
    }

    /**
     * Gets the trigger word.
     *
     * @return the trigger word
     */
    public String getTrigger_word() {
        return trigger_word;
    }

    /**
     * Sets the trigger word.
     *
     * @param trigger_word
     *            the new trigger word
     */
    public void setTrigger_word( String trigger_word ) {
        this.trigger_word = trigger_word;
    }

    /** The trigger word. */
    private String trigger_word;

}
