package ai.javis.project.library_management_system.services;

import ai.javis.project.library_management_system.payloads.ApiResponse;
import ai.javis.project.library_management_system.payloads.LendBookRequest;
import ai.javis.project.library_management_system.payloads.ReturnBookRequest;
import ai.javis.project.library_management_system.payloads.TxnDto;

import java.util.List;
public interface TxnService {
    ApiResponse lendBook(LendBookRequest lendBookRequest) throws Exception;
    ApiResponse returnBook(ReturnBookRequest returnBookRequest) throws Exception;
    List<TxnDto> getLendingRecords() throws Exception;
    List<TxnDto> getLendingRecordsByUserId(Integer userId) throws Exception;


}
