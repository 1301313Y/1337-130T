# 1337-130T
A open source multi-indicator cryptocurrency bot using NodeJS and Python. (In Development)

## (In Development) (NO ETA)
I will update this description as I progress.

## Mission Statement
1337 130T is planning to be an open source cryptocurrency bot using NodeJS and Python. Which as you may be thinking, with a quick search on github you can find dozens of other options, what will make this different? Well from my obervations, many rely on a single idicator which can lead to profit losses over time. While some do allow for user created indicators, it becomes clunky when trying to apply multiple stratgies. Then comes the user friendliness factor, as many free options avaialble are command line tools, which have wonderful potential, though many users find them difficult to work with. These are the problem in which I intend to aid. First by implmenting a easy to use multi-indicator structure, which will allow users to run multiple stratgies side-by-side, like for example _MACD & RSI_. Secondly creating a user-friendly interface, which allows users, even who dislike command line tools, to run the application with ease.

## The Problems I Hope To Fix
As I said earlier in the mission statement, many other bots available `rely on a single idicator which can lead to profit losses over time`. This means that relying on a single indicator, such a `RSI`, `MACD`. or `Stochastics Oscillator`, can lead to losses due to the bots inablity to see other environmental factors. This is why I believe a multi-indicator structure can lead to better results. Below we can see an example of this problem using an `Stochastics Oscillator` over `500` `15-minute` periods trading `XVG/ETH`.
![Screenshot](https://github.com/1301313Y/binance-trader/blob/master/img/stoch_graph_example.PNG)

## Design Structure
When it comes to design, I'm a visual person. I like to see what something will look like before I ever start working. Which is why I hope this structure will help some people see the basic concept of the design. While this design is basic, and does not include much destail, it will be the skeleton of this project. _Please note this design is subject to change!_

![Screenshot](https://i.imgur.com/dUdIz4N.png)

## Planned Exchanges (In Order)
1. [Binance](https://www.binance.com)
2. [Kraken](https://www.kraken.com/)
3. TBA
