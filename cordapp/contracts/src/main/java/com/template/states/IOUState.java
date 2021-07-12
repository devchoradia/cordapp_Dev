package com.template.states;

import com.template.contracts.TemplateContract;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;

import java.util.Arrays;
import java.util.List;

@BelongsToContract(IOUContract.class)
public class IOUState implements ContractState {

    //private variables
    private final int LCValue;
    private final Party exporter;
    private final Party importer;
    private final Party issuingBank;
    private final Party shippingAgency;


    public IOUState(int LCValue, Party exporter, Party importer, Party issuingBank, Party shippingAgency) {
        this.LCValue = LCValue;
        this.exporter = exporter;
        this.importer = importer;
        this.issuingBank = issuingBank;
        this.shippingAgency = shippingAgency;
    }

    public int getLCValue() {
        return LCValue;
    }

    public Party getExporter() {
        return exporter;
    }

    public Party getImporter() {
        return importer;
    }

    public Party getIssuingBank() {
        return issuingBank;
    }

    public Party getShippingAgency() {
        return shippingAgency;
    }


    /* This method will indicate who are the participants and required signers when
     * this state is used in a transaction. */
    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(exporter,importer,issuingBank,shippingAgency);
    }
}