# Lab 6 

## Ex01

| Issue | Problem description | How to solve|
| --- | --- | --- |
| Major Code Smell | "Preconditions" and logging arguments should not require evaluation | Use the `isDebugEnabled()` method to check if the debug level is enabled before evaluating the arguments. |
| Major Code Smell | "for" loop stop conditions should be invariant | Put i++ in the for loop instead of the end of the loop. |
|Vulnerability|Using pseudorandom number generators (PRNGs) is security-sensitive | Use SecureRandom instead of Random. |


## Ex02

Technical Debt - 30min