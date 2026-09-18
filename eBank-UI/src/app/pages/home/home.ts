
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { LucideAngularModule, Tickets, Users } from 'lucide-angular';
import { CardModule } from 'primeng/card';
import { TableModule } from 'primeng/table';

@Component({
  selector: 'app-home',
  imports: [ LucideAngularModule , CardModule , TableModule , CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  Users= Users 
  Tickets = Tickets
  recentTransactions = [
    {id : 1991 , to : "mohamed" , from : "mohamed" , date : new Date() , time : "12:00:00" , amount :  10000}
  ]
}
