/**
 * Enhanced Main Layout Component
 * Handles top navigation, user menu, and responsive sidebar toggle
 */

import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Menu, MenuModule} from 'primeng/menu';
import { MenuItem } from 'primeng/api';
import { AvatarModule } from 'primeng/avatar';
import { ButtonModule } from 'primeng/button';
import { Header } from '@/components/header/header';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.html',
  styleUrls: ['./layout.css'], 
  imports: [RouterModule, MenuModule, AvatarModule, ButtonModule, Header]
})
export class Layout implements OnInit {

  @ViewChild('userMenu') userMenu!: Menu;

  // User Information
  userFullName = 'Mohamed Ahmed';
  userFirstName = 'Mohamed';
  userRole = 'Admin';
  userProfileImage = 'https://primefaces.org/cdn/primeng/images/demo/avatar/asiyajavayant.png';

  // Current Page (for breadcrumb)
  currentPage = 'Dashboard';

  // Notifications
  notificationCount = 3;

  // User Menu Items
  userMenuItems: MenuItem[] = [];

  // Sidebar state
  isSidebarOpen = true;

  constructor(
    private router: Router
  ) {}

  ngOnInit() {
    this.initializeUserMenu();
    this.updateCurrentPage();
  }

  /**
   * Initialize user dropdown menu items
   */
  private initializeUserMenu() {
    this.userMenuItems = [
      {
        label: 'Profile',
        icon: 'pi pi-user',
        command: () => this.navigateTo('/profile')
      },
      {
        label: 'Settings',
        icon: 'pi pi-cog',
        command: () => this.navigateTo('/settings')
      },
      {
        label: 'My Account',
        icon: 'pi pi-id-card',
        command: () => this.navigateTo('/account')
      },
      {
        separator: true
      },
      {
        label: 'Logout',
        icon: 'pi pi-sign-out',
        styleClass: 'text-red-600',
        command: () => this.logout()
      }
    ];
  }

  /**
   * Toggle sidebar visibility
   */
  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
    // Emit event to parent to toggle sidebar visibility
    // You can use a service for this in a real app
    console.log('Toggle sidebar:', this.isSidebarOpen);
  }

  /**
   * Toggle user menu dropdown
   */
  toggleUserMenu(event: Event) {
    this.userMenu.toggle(event);
  }

  /**
   * Toggle mobile sidebar
   */
  toggleMobileSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  /**
   * Navigate to a route
   */
  private navigateTo(route: string) {
    this.router.navigate([route]);
  }

  /**
   * Logout user
   */
  private logout() {
    // Call logout API
    console.log('Logging out...');
    this.router.navigate(['/login']);
  }

  /**
   * Update current page based on route
   */
  private updateCurrentPage() {
    const routeMap: { [key: string]: string } = {
      '/': 'Dashboard',
      '/accounts': 'Accounts',
      '/users': 'Users',
      '/transactions': 'Transactions',
      '/settings': 'Settings',
      '/profile': 'Profile'
    };

    this.currentPage = routeMap[this.router.url] || 'Dashboard';

    // Update on route changes
    this.router.events.subscribe(() => {
      this.currentPage = routeMap[this.router.url] || 'Dashboard';
    });
  }

}