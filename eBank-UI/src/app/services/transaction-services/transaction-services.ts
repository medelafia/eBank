import { OperationRequest, OperationResponse } from '@/models/operation';
import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TransactionServices {
  readonly httpClient : HttpClient = inject(HttpClient)
  readonly baseUrl : string = "http://localhost:8085/api/v1/account"


  public deposit(accountId: string ,operation : OperationRequest) : Observable<OperationResponse> {
    return this.httpClient.post<OperationResponse>(this.baseUrl + `/${accountId}`, operation) 
  }
  
}
