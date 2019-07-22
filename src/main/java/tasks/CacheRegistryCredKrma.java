package tasks;/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public static void main(String[] args) {
        CacheRegister register = new CacheRegister();
        register.add(25, 3);
        register.add(1, 10);


        List<Coin> result = register.change(79);

        for (Coin currCoin : result) {
            System.out.println(currCoin.number + "," + currCoin.coins);
        }
    }

    static class CacheRegister {
        Map<Integer, Integer> coins = new HashMap<>();
        List<Integer> coinTypes = new ArrayList<>();

        CacheRegister() {
            coinTypes.add(25);
            coinTypes.add(10);
            coinTypes.add(5);
            coinTypes.add(1);
        }

        List<Coin> change(int number) {
            Map<Integer, Coin> result = new HashMap<>();

            int leftNumber = number;

            int currentCoinTypeIndex = -1;
            int currentCoinType = 0;

            while (true) {
                currentCoinTypeIndex += 1;

                if (currentCoinTypeIndex == coinTypes.size()) {
                    throw new IllegalStateException("Not enough money");
                }

                currentCoinType += coinTypes.get(currentCoinTypeIndex);


                if (coins.get(currentCoinType) == null) {
                    continue;
                }
                int currentLeft = coins.get(currentCoinType);


                if (leftNumber > currentLeft) {
                    int coinNumbers = leftNumber / currentLeft;
                    int leftNumberRest = leftNumber % currentLeft;

                    if (!coins.keySet().contains(currentLeft)) {
                        coins.put(currentLeft, 0);
                    }

                    if (coins.get(currentLeft) - coinNumbers < 0) {
                        continue;
                    }

                    coins.put(currentLeft, coins.get(currentLeft) - coinNumbers);


                    Coin currCoins = result.get(currentLeft);
                    result.put(currentLeft, new Coin(currentCoinType, coinNumbers));

                    leftNumber = leftNumberRest;
                }

            }

        }

        void add(int coinType, int coin) {
            coins.put(coinType, coin);
        }
    }

    static class Coin {
        int number;
        int coins;

        Coin(int number, int coins) {
            this.number = number;
            this.coins = coins;
        }
    }
}


/*
Please implement a Cash Register that can display the count of all the changes in the register and gives the correct changes for any specified amount.

Question #1:
==============
1. The cash register will start with a specified number of coins
2. Allow Cash Register to accept different set of coins
3. The cash register can  "Get Change," which means we will specify a number as input and it returns the coins for the specified amount

Test Cases for Question #1:
=============================
Cash Register starts with:
- 25c x 3
- 10c x 10
- 5c x 10
- 1c x 10

Input for "Get Change":
   Change: 0.79,
Should Output:
   25c: 3
   1c: 4

Then again with Input:
   Change: 0.75
Should Output (Note: Cash Register doesn't have 25c anymore after the last request):
   10c: 7
   5c: 1


Question #2:
==============
A little harder example

Test Case for Question #2:
=================================
Start With:: 
- 25c: 3 
- 10c: 10
- 1c: 3
Input: 0.40
Change:  10c: 4
*/
