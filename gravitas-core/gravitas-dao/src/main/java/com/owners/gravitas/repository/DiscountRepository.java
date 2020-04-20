package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.Discount;

/**
 * The Interface DiscountRepository.
 *
 * @author kushwaja
 */
public interface DiscountRepository extends JpaRepository< Discount, String > {

    /**
     * @param state
     * @return
     */
    List< Discount > findByState( final String state );

    /**
     * @param state
     * @param startPriceRange
     * @param endPriceRange
     * @return
     */
    List< Discount > findByStateAndMinumumSalePriceBetweenOrderByMinumumSalePriceDesc( final String state, final int startPriceRange,
            final int endPriceRange );
    
    /**
     * @param state
     * @param endPriceRange
     * @return
     */
    List< Discount > findByStateAndMinumumSalePriceLessThanEqualOrderByMinumumSalePriceDesc( final String state, final int endPriceRange );

}
