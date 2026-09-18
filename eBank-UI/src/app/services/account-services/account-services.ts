import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Account } from '../../models/account';
import { OperationRequest } from '../../models/operation';

@Injectable({
  providedIn: 'root',
})
export class AccountServices {
  readonly httpClient : HttpClient = inject(HttpClient)
  readonly baseUrl : string = "http://localhost:8085/api/v1/account"

  public getAccountById(accountId : string) : Observable<Account> {
    return this.httpClient.get<Account>(this.baseUrl + `/${accountId}`)
  }
  public getAllAccounts() : Observable<Account[]> { 
    return this.httpClient.get<Account[]>(this.baseUrl )
  }
  public createAccount(account : Account) : Observable<Account> { 
    return this.httpClient.post<Account>(this.baseUrl , account)
  }
  public updateAccount(account : Account) : Observable<Account> { 
    return this.httpClient.put<Account>(this.baseUrl , account)
  }
  public deleteAccount(accountId : string) : Observable<any> { 
    return this.httpClient.delete(this.baseUrl + `/${accountId}`)
  }
  public withdraw(operation : OperationRequest) : Observable<Account> {
    return this.httpClient.post<Account>(this.baseUrl + "/withdraw", operation )
  }
  public deposit(operation : OperationRequest) : Observable<Account> {
    return this.httpClient.post<Account>(this.baseUrl + "/deposit" , operation )
  }
  public getAllAccountsyUserId(userId : string) : Observable<Account[]> { 
    return this.httpClient.get<Account[]>(this.baseUrl + `/user/${userId}`)
  }
}
