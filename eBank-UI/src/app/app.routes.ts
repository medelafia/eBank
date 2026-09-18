import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Accounts } from './pages/accounts/accounts';
import { Users } from './pages/users/users';
import { Login } from './pages/login/login';
import { authGuard } from './guards/auth-guard';
import { Layout } from './pages/layout/layout';
import { Transactions } from './pages/transactions/transactions';
import { Client } from './pages/client/client';

export const routes: Routes = [
    {   
        path : "" , 
        canActivateChild : [authGuard] ,
        component : Layout , 
        children : [ 
            { path : "" , component : Home } ,
            { path : "accounts" , component : Accounts } , 
            { path : "users" , component : Users } , 
            { path : "transactions" , component : Transactions}
        ]
    } , 
    { path : "login" , component : Login }, 
    { path : "client/:id" , component : Client}
];
