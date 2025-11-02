package br.fatec.edu.ecommerce.validator;

import br.fatec.edu.ecommerce.model.Voucher;

public class VoucherValidator {

    /**
     * Valida se o Voucher é válido
     * @param voucher Voucher a ser validado
     * @return true se o voucher for válido, false caso contrário
     */
    public boolean isValid(Voucher voucher) {
        if (voucher == null) {
            return false;
        }
        
        return hasValidType(voucher) && hasValidValue(voucher);
    }

    /**
     * Valida se o tipo do voucher é válido (não nulo)
     * @param voucher Voucher a ser validado
     * @return true se o tipo for válido, false caso contrário
     */
    public boolean hasValidType(Voucher voucher) {
        return voucher != null && voucher.getType() != null;
    }

    /**
     * Valida se o valor do voucher é válido (não negativo)
     * @param voucher Voucher a ser validado
     * @return true se o valor for válido, false caso contrário
     */
    public boolean hasValidValue(Voucher voucher) {
        if (voucher == null) {
            return false;
        }
        
        double value = voucher.getValue();
        return value >= 0;
    }

    /**
     * Valida se o voucher é do tipo FIXED_AMOUNT
     * @param voucher Voucher a ser validado
     * @return true se for FIXED_AMOUNT, false caso contrário
     */
    public boolean isFixedAmount(Voucher voucher) {
        if (!hasValidType(voucher)) {
            return false;
        }
        
        return voucher.getType() == Voucher.Type.FIXED_AMOUNT;
    }

    /**
     * Valida se o voucher é do tipo PERCENTAGE
     * @param voucher Voucher a ser validado
     * @return true se for PERCENTAGE, false caso contrário
     */
    public boolean isPercentage(Voucher voucher) {
        if (!hasValidType(voucher)) {
            return false;
        }
        
        return voucher.getType() == Voucher.Type.PERCENTAGE;
    }

    /**
     * Valida se o valor do voucher percentual está no range válido (0 a 100)
     * @param voucher Voucher a ser validado
     * @return true se o valor percentual for válido, false caso contrário
     */
    public boolean hasValidPercentageValue(Voucher voucher) {
        if (!isPercentage(voucher)) {
            return false;
        }
        
        double value = voucher.getValue();
        return value >= 0 && value <= 100;
    }

    /**
     * Valida se o valor do voucher de valor fixo é positivo
     * @param voucher Voucher a ser validado
     * @return true se o valor fixo for positivo, false caso contrário
     */
    public boolean hasPositiveFixedAmount(Voucher voucher) {
        if (!isFixedAmount(voucher)) {
            return false;
        }
        
        return voucher.getValue() > 0;
    }

    /**
     * Valida se o voucher pode ser aplicado (tem tipo e valor válidos)
     * @param voucher Voucher a ser validado
     * @return true se pode ser aplicado, false caso contrário
     */
    public boolean canBeApplied(Voucher voucher) {
        if (!isValid(voucher)) {
            return false;
        }
        
        // Para ser aplicável, precisa ter valor maior que zero
        return voucher.getValue() > 0;
    }
}

