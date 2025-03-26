package com.redhat.demo;

import java.math.BigDecimal;
import java.time.LocalDate;
/**
 * Record representing a lease report containing key information extracted from a lease agreement.
 *
 * @param agreementDate The date when the lease agreement was signed
 * @param termStartDate The date when the lease term begins
 * @param termEndDate The date when the lease term ends
 * @param developmentTermEndDate The end date of any development/construction period
 * @param landlordName The name of the landlord/property owner
 * @param tenantName The name of the tenant/lessee
 * @param address The address of the leased property
 * @param rent The month's rent 
 * @param petsFee The month's fee if pets are allowed
 * @param petsAllowed Pets are allowed
 * @param exclusiveRights Whether the tenant has exclusive rights to the property
 */
public record LeaseReport(
        LocalDate agreementDate,
        LocalDate termStartDate,
        LocalDate termEndDate,
        LocalDate developmentTermEndDate,
        String landlordName,
        String tenantName,
        String address,
        BigDecimal rent,
        Boolean petsAllowed,
        BigDecimal petsFee,
        Boolean exclusiveRights) {
}
