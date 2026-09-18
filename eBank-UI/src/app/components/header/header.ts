
import { LucideAngularModule, Landmark } from 'lucide-angular';
import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router, RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { filter } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  imports: [ ButtonModule , RouterLink , LucideAngularModule, CommonModule],
  templateUrl: './header.html',
  styleUrl: './header.css',
  
})
export class Header implements OnInit{
  Landmark = Landmark
  router : Router = inject(Router)
  currentRoute? : string
  toggleDarkMode() {  
    const element = document.querySelector('html');
    element?.classList.toggle('app-dark');
  }
  ngOnInit(): void {
    this.router.events
        .pipe(filter(event => event instanceof NavigationEnd))
        .subscribe((event : NavigationEnd) => {
          console.log(event.url)
          this.currentRoute = event.url
        })
  }
}
