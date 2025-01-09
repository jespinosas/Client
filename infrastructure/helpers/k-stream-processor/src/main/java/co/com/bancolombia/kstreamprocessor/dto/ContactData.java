package co.com.bancolombia.kstreamprocessor.dto;


public record ContactData(TextType INFA_SEQUENCE, TextType INFA_OP_TYPE, TextType infa_operation_owner,
                          TextType infa_operation_time, TextType infa_operation_type, TextType INFA_TABLE_NAME,
                          TextType infa_transaction_id, TextType BARRIO, boolean BARRIO_Present,
                          TextType NUMERO_DOCUMENTO, TextType CD_TIPO_DOCUMENTO) {

    public record TextType(String string){}
}
