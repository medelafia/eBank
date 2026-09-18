import { User } from '@/models/user';
import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserServices {
  private readonly httpClient : HttpClient = inject(HttpClient)
  private readonly baseUrl : string = "http://localhost:8083/api/v1/user"

  public saveUser(user : User) : Observable<any> {
    return this.httpClient.post(this.baseUrl , user)
  }

  public getAllUsers() : Observable<any> { 
    return this.httpClient.get(this.baseUrl)
  }
  public deteleUserById(userId : string) : Observable<any> { 
    return this.httpClient.delete(this.baseUrl + `/${userId}`)
  }
  public editUser(user : User) : Observable<any> { 
    return this.httpClient.put(this.baseUrl , user )
  }
  public getUserById(userId : string ) : Observable<any> { 
    return this.httpClient.get(this.baseUrl + `/${userId}`)
  }
}
