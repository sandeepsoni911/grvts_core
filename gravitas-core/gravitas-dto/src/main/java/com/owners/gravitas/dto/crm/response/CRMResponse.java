package com.owners.gravitas.dto.crm.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.response.BaseResponse;

/**
 * CRM response bean.
 *
 * @author vishwanathm
 *
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class CRMResponse extends BaseResponse {

    /** serialVersionUID. **/
    private static final long serialVersionUID = -6787307576371650520L;

    /** The total size. */
    private int totalSize;

    /** The done. */
    private boolean done;

    /** The records. */
    private List< Map< String, Object > > records = new ArrayList< >();

    /**
     * Gets the total size.
     *
     * @return the total size
     */
    public int getTotalSize() {
        return totalSize;
    }

    /**
     * Sets the total size.
     *
     * @param totalSize
     *            the new total size
     */
    public void setTotalSize( final int totalSize ) {
        this.totalSize = totalSize;
    }

    /**
     * Checks if is done.
     *
     * @return true, if is done
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Sets the done.
     *
     * @param done
     *            the new done
     */
    public void setDone( final boolean done ) {
        this.done = done;
    }

    /**
     * Gets the records.
     *
     * @return the records
     */
    public List< Map< String, Object > > getRecords() {
        return records;
    }

    /**
     * Sets the records.
     *
     * @param records
     *            the new records
     */
    public void setRecords( final List< Map< String, Object > > records ) {
        this.records = records;
    }
}
