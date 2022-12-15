# python-polyglot
A silly example to compare a benchmark between pure python and python on graalvm's native image.

# Usage
You can use the Makefile targets to build and run the images:
- `make run-native` builds the docker image for the thin Java wrapper for the python code. You can tweak the number of iterations.
by setting the environment variables `ITERATIONS` (default 5) and `ARGUMENTS` (default 3).
- `make run-python` similarly builds the docker image for the Python code and runs the test. Same environment variables as
in the previous.
  
# Findings
On my (not very scientific) comparison, I did various tests and my observations were:
- the higher the ARGUMENTS (i.e. the costlier each iteration), the native-image generated binary is much faster.
An example is that `ARGUMENT=10 ITERATIONS=10 make run-native` takes between high 9 and low 10s whereas `ARGUMENT=10 ITERATIONS=10 make run-python`
took almost 68s to me (i.e. almost 7 times more!)
- for cheaper evaluations with low number of calls, python seems to out-perform the native image:

| ARGUMENT | ITERATIONS | time native (ms) | time python (ms) |
| -------- |:----------:|:----------------:|-----------------:|
| 1        | 100        | 4                | 0                |
| 1        | 1000       | 23               | 2                |
| 1        | 1000       | 23               | 2                |
| 1        | 10000      | 121              | 24               |
| 1        | 100000     | 426              | 237              |
| 1        | 1000000    | 3154             | 2564             |
| 1        | 10000000   | 29337            | 24010            |
| 2        | 10000000   | 32004            | 35996            |
| 3        | 1000000    | 3939             | 7430             |
| 4        | 100000     | 807              | 2622             |
| 5        | 10000      | 307              | 1338             |

As one can observe, the more complex the operation or the higher the number of iterations, the native image gets closer
and surpasses the python image.

More tests need to be made to assess:
- how does it behave when using libraries
- more complex types of input / output of the function
- bests ways to test the different parts of the code
- using a framework (for example quarkus) to perform the "standard tasks"

