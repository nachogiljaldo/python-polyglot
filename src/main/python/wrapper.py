"""
The Computer Language Benchmarks Game
http://benchmarksgame.alioth.debian.org/
Contributed by Sokolov Yura, modified by Tupteq.
"""
from benchmark import fannkuch
import os
import time

DEFAULT_ARG = 5

import time

def current_milli_time():
    return round(time.time() * 1000)

if __name__ == "__main__":
    iterations = os.environ.get('ITERATIONS', 3)
    argument = os.environ.get('ARGUMENT', DEFAULT_ARG)
    print('Running {} with argument {}'.format(iterations, argument))
    start = current_milli_time()
    for i in list(range(1, int(iterations))):
        print('{} - {} - {}'.format(current_milli_time(), i, fannkuch.fannkuch(int(argument))))
    print("Finished in {}ms".format(current_milli_time() - start))
