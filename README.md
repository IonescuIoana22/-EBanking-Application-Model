# -EBanking-Application-Model

## Introduction
This project implements functionalities for a mobile eBanking application. Users can create accounts, add money to their accounts, transfer funds to other users, exchange currencies, purchase stocks, and receive stock purchase recommendations based on market trends. The application is designed using object-oriented programming principles and design patterns.

## Functionality

### Core Entities

#### User
- Uniquely identified by email (no two users can have the same email)
- Attributes: email, first name, last name, address
- Portfolio containing:
  - Accounts in various currencies (supported currencies are provided in the input file)
  - Stocks (companies whose stocks can be purchased are provided in the input file)
- Friends list (other users to whom the user can transfer money)

#### Account
- Currency type (must be from the list of currencies supported by the application)
- Balance

#### Stocks
- Company name
- List of values for the last 10 days

## Commands

### User and Account Management Commands

| Command | Format | Description | Error Handling |
|---------|--------|-------------|---------------|
| Create User | `CREATE USER <email> <firstname> <lastname> <address>` | Creates a new user | If email already exists: "User with \<email\> already exists" |
| Add Friend | `ADD FRIEND <emailUser> <emailFriend>` | Adds another user as a friend (friendship request is automatically accepted) | If user doesn't exist: "User with \<emailUser\> doesn't exist"<br>If already friends: "User with \<emailFriend\> is already a friend" |
| Add Account | `ADD ACCOUNT <email> <currency>` | Adds an account in a specific currency | If account already exists: "Account in currency \<currency\> already exists for user" |
| Add Money | `ADD MONEY <email> <currency> <amount>` | Deposits money into an account | (Assumed to always succeed) |
| Exchange Money | `EXCHANGE MONEY <email> <sourceCurrency> <destinationCurrency> <amount>` | Exchanges money between own accounts | If insufficient funds: "Insufficient amount in account \<sourceCurrency\> for exchange"<br>Note: 1% commission if transfer exceeds 50% of source account balance |
| Transfer Money | `TRANSFER MONEY <email> <friendEmail> <currency> <amount>` | Transfers money to a friend | If insufficient funds: "Insufficient amount in account \<currency\> for transfer"<br>If not friends: "You are not allowed to transfer money to \<emailFriend\>" |
| Buy Stocks | `BUY STOCKS <email> <company> <noOfStocks>` | Purchases stocks (always in USD) | If insufficient funds: "Insufficient amount in account for buying stocks" |

### Stock Recommendation Command

| Command | Format | Output |
|---------|--------|--------|
| Recommend Stocks | `RECOMMEND STOCKS` | Returns a JSON list of recommended stocks based on Simple Moving Averages (SMAs) Crossover Strategy |

### Listing Commands

| Command | Format | Output | Error Handling |
|---------|--------|--------|---------------|
| List User | `LIST USER <email>` | Returns user details in JSON format | If user doesn't exist: "User with \<emailUser\> doesn't exist" |
| List Portfolio | `LIST PORTFOLIO <email>` | Returns user's portfolio in JSON format | If user doesn't exist: "User with \<emailUser\> doesn't exist" |

### Premium Features (Bonus)

| Command | Format | Description | Error Handling |
|---------|--------|-------------|---------------|
| Buy Premium | `BUY PREMIUM <email>` | Purchases premium features for $100 USD | If user doesn't exist: "User with \<email\> doesn't exist"<br>If insufficient funds: "Insufficient amount in account for buying premium option" |

#### Premium Benefits
- No commission on currency exchange (normal accounts have a 1% fee if the amount exceeds half of the source account)
- 5% discount when purchasing recommended stocks

## Input Data
- Supported currencies: USD, EUR, GBP, JPY, CAD
- Exchange rates provided in `exchangeRates.csv`
- Stock values for the last 10 days provided in `stockValues.csv`
- Commands provided in `commands.txt`

## Project Structure
- The main method should be in the `Main` class
- Command-line arguments: `exchangeRates.csv stockValues.csv commands.txt`