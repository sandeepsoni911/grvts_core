package com.owners.gravitas.dto.request;

import java.util.List;

/**
 * The class CoShoppingLeadUpdateRequest.
 * 
 * @author imranmoh
 *
 */
public class CoShoppingLeadUpdateRequest extends BaseRequest {

    /*** The serialVersionUID */
    private static final long serialVersionUID = 878968910332983364L;

    /** The co shopping lead update request */
    private List< CoShoppingLeadUpdateModel > leadUpdateRequest;

    /**
     * @return the leadUpdateRequest
     */
    public List< CoShoppingLeadUpdateModel > getLeadUpdateRequest() {
        return leadUpdateRequest;
    }

    /**
     * @param leadUpdateRequest
     *            the leadUpdateRequest to set
     */
    public void setLeadUpdateRequest( final List< CoShoppingLeadUpdateModel > leadUpdateRequest ) {
        this.leadUpdateRequest = leadUpdateRequest;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CoShoppingLeadUpdateRequest [leadUpdateRequest=" + leadUpdateRequest + "]";
    }
}
