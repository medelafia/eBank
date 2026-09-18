# 💳 eBank-UI

![Angular](https://img.shields.io/badge/Angular-19+-red)
![TypeScript](https://img.shields.io/badge/TypeScript-5.x-blue)
![PrimeNG](https://img.shields.io/badge/PrimeNG-Latest-orange)
![RxJS](https://img.shields.io/badge/RxJS-7.x-purple)
![Tailwind CSS](https://img.shields.io/badge/Tailwind-CSS-06B6D4)
![License](https://img.shields.io/badge/License-MIT-green)

A modern, responsive Angular-based banking user interface for the **Banking Microservice System**. Built with Angular 19+, PrimeNG components, and Tailwind CSS, providing a seamless customer experience for account and transaction management.

---

# ✨ Features

- 👤 **User Authentication & Profile Management**
- 💳 **Account Management** (Create, View, Edit, Delete)
- 💸 **Deposit & Withdrawal Operations**
- 🔄 **Transaction History**
- 📊 **Account Dashboard**
- 🌐 **Responsive Design** (Mobile, Tablet, Desktop)
- 🎨 **Modern UI with PrimeNG Components**
- 🔐 **Form Validation & Error Handling**
- 📱 **Real-time Account Updates**
- 🔔 **Toast Notifications**
- ⚡ **Reactive Forms (FormGroup & FormControl)**
- 🌙 **Theme Support** (Light/Dark Mode)

---

# 🧩 Key Components

## Accounts Component

Manages bank account operations with a data table and dialog forms.

**Features:**
- Display all user accounts
- Create new accounts
- Edit account details
- Delete accounts
- Real-time balance updates
- Account type & status management

**Technologies:**
- Reactive Forms (FormGroup, FormControl)
- PrimeNG Table & Dialog
- RxJS Observables

---

## Transactions Component

Displays transaction history and enables money transfers.

**Features:**
- Transaction list with filtering
- Transaction details view
- Deposit/Withdraw operations
- Transaction status tracking

---

## Dashboard Component

Overview of user accounts and recent transactions.

**Features:**
- Account summary
- Total balance calculation
- Recent transactions
- Quick actions

---

# ⚙️ Technologies

**Frontend Framework**
- Angular 19+
- TypeScript 5.x
- RxJS 7.x

**UI Components & Styling**
- PrimeNG
- Tailwind CSS
- Bootstrap Icons

**Form Management**
- Reactive Forms
- Form Validation

**HTTP Client**
- Angular HttpClient
- HTTP Interceptors

**State Management**
- RxJS Subjects & BehaviorSubjects
- Services (Optional: NgRx for larger projects)

**Development Tools**
- Angular CLI
- npm / yarn
- ESLint & Prettier

---

# 🚀 Getting Started

## Prerequisites

- Node.js (v18 or higher)
- npm or yarn
- Angular CLI

```bash
npm install -g @angular/cli
```

---

## Installation

### 1. Clone the repository

```bash
git clone https://github.com/medelafia/eBank-UI.git
cd eBank-UI
```

### 2. Install dependencies

```bash
npm install
```

### 3. Configure API endpoints

Update `src/environments/environment.ts`:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'  // API Gateway URL
};
```

### 4. Start the development server

```bash
ng serve --open
```

The application will open at `http://localhost:4200`

---

## Development

### Generate new component

```bash
ng generate component components/my-component
ng g c components/my-component
```

### Generate new service

```bash
ng generate service services/my-service
ng g s services/my-service
```

### Build for production

```bash
ng build --configuration production
```

---

# 📡 API Integration

The UI communicates with the **Banking Microservice** via REST APIs through the API Gateway.

### Environment Configuration

**Development:**
```
API_URL: http://localhost:8080/api
```

**Production:**
```
API_URL: https://api.ebank.com/api
```

### Key API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/users` | GET | Get all users |
| `/users/{id}` | GET | Get user by ID |
| `/accounts` | GET | Get all accounts |
| `/accounts` | POST | Create account |
| `/accounts/{id}` | PUT | Update account |
| `/accounts/{id}` | DELETE | Delete account |
| `/transactions` | GET | Get all transactions |
| `/transactions` | POST | Create transaction |

---

# 🔐 Security

- **Authentication Guard** - Protects routes requiring login
- **HTTP Interceptor** - Adds authorization tokens to requests
- **Input Validation** - Reactive form validation
- **HTTPS** - Use HTTPS in production
- **CORS** - Properly configured on backend

---

# 🧪 Testing

### Run unit tests

```bash
ng test
```

### Run e2e tests

```bash
ng e2e
```

### Code coverage

```bash
ng test --code-coverage
```

---

# 📦 Dependencies

Key npm packages:

```json
{
  "@angular/core": "^19.0.0",
  "@angular/forms": "^19.0.0",
  "@angular/common": "^19.0.0",
  "@angular/router": "^19.0.0",
  "primeng": "^latest",
  "tailwindcss": "^3.x",
  "rxjs": "^7.x",
  "typescript": "^5.x"
}
```

---

# 🎨 Theming

### Customize theme

1. Update `src/styles/global.css` for global styles
2. Modify `tailwind.config.js` for Tailwind configuration
3. Use PrimeNG theme variables

Example:
```css
:root {
  --primary-color: #3b82f6;
  --secondary-color: #10b981;
}
```

---

# 📱 Responsive Design

The UI is fully responsive and tested on:
- 📱 Mobile (320px - 768px)
- 📱 Tablet (768px - 1024px)
- 🖥️ Desktop (1024px+)

Uses Tailwind CSS breakpoints:
- `sm`, `md`, `lg`, `xl`, `2xl`

---

# 🐳 Docker Support

### Build Docker image

```bash
docker build -t ebank-ui:latest .
```

### Run Docker container

```bash
docker run -p 4200:80 ebank-ui:latest
```

### Docker Compose

```bash
docker-compose up ebank-ui
```

---

# 📈 Performance Optimization

- **Lazy Loading** - Route-based code splitting
- **Change Detection Strategy** - OnPush where applicable
- **TreeShaking** - Remove unused code in production
- **Minification** - Automatic with production build
- **Compression** - Gzip compression on server

---

# 🔄 State Management (Optional)

For larger applications, consider integrating **NgRx**:

```bash
ng add @ngrx/store
```

---

# 📚 Common Tasks

### Add authentication

```typescript
// Create auth service
ng g s services/auth

// Create auth guard
ng g guard guards/auth
```

### Add error handling

```typescript
// Create error interceptor
ng g interceptor interceptors/error
```

### Add HTTP interceptor

```typescript
// Add authorization header
ng g interceptor interceptors/auth
```

---

# 🐛 Troubleshooting

### CORS Issues
- Ensure backend is configured to allow CORS from `localhost:4200`
- Check `Access-Control-Allow-Origin` headers

### API Connection Failed
- Verify backend is running on configured port
- Check API_URL in environment configuration
- Review browser console for error details

### Form Validation Not Working
- Ensure `ReactiveFormsModule` is imported
- Check FormControl names match HTML form names
- Verify validators are properly assigned

---

# 📈 Future Improvements

- [ ] Redux/NgRx for state management
- [ ] JWT Token refresh mechanism
- [ ] Advanced search & filtering
- [ ] Export to PDF/Excel
- [ ] Push notifications
- [ ] Progressive Web App (PWA)
- [ ] Biometric authentication
- [ ] Two-factor authentication
- [ ] Account transfer feature
- [ ] Bill payment integration
- [ ] Loan applications
- [ ] Investment portfolio
- [ ] Unit & E2E tests
- [ ] Accessibility (WCAG)
- [ ] Performance monitoring
- [ ] Analytics integration

---

# 📚 Learning Resources

- [Angular Documentation](https://angular.io/docs)
- [TypeScript Handbook](https://www.typescriptlang.org/docs/)
- [RxJS Documentation](https://rxjs.dev/)
- [PrimeNG Components](https://primeng.org/)
- [Tailwind CSS](https://tailwindcss.com/)

---

# 🤝 Contributing

1. Fork the repository
2. Create a feature branch
   ```bash
   git checkout -b feature/my-feature
   ```
3. Commit changes
   ```bash
   git commit -m "Add new feature"
   ```
4. Push to branch
   ```bash
   git push origin feature/my-feature
   ```
5. Open a Pull Request

---

# 👨‍💻 Author

**Mohamed El Afia**

- GitHub: [@medelafia](https://github.com/medelafia)
- LinkedIn: [Mohamed El Afia](https://linkedin.com/in/medelafia)

---

# 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

# ⭐ Support

If you found this project useful, consider giving it a ⭐ on GitHub!

Questions? Feel free to open an issue or reach out!

---

## 🚀 Quick Start Recap

```bash
# Clone
git clone https://github.com/medelafia/eBank-UI.git
cd eBank-UI

# Install
npm install

# Configure
# Edit src/environments/environment.ts with your API URL

# Run
ng serve --open

# Build
ng build --configuration production

# Docker
docker build -t ebank-ui:latest .
docker run -p 4200:80 ebank-ui:latest
```

Happy Banking! 🏦💰