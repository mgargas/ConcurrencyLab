var async = require("async");

var Fork = function() {
    this.state = 0;
    this.maxTryNumber = 5;
    this.maxWaitTime = 2048;
    return this;
};

Fork.prototype.acquire = function(cbs) {
    // zaimplementuj funkcje acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwsza proba podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy proba jest nieudana, zwieksza czas oczekiwania dwukrotnie
    //    i ponawia probe itd.
    var tryNumber = 0;
    var getFork = function (waitTime, fork) {
        setTimeout(function () {
            if (fork.state === 1){
                tryNumber++;
                time = Math.pow(2, tryNumber);
                //console.log("time: " + time);
                getFork(time , fork);
            }else {
                fork.state = 1;
                if(cbs) cbs()
            }
        }, waitTime);
    };
    getFork(1, this);
};

Fork.prototype.release = function(cb) {
    this.state = 0;
    if(cb) cb();
};

var Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id+1) % forks.length;
    this.eatTime = 20;
    return this;
};

Philosopher.prototype.eatNaive = function (count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    console.log(id + " is eating");
    setTimeout(function () {
        async.waterfall([
            function (cb) {
                forks[f1].release(cb);
            },
            function (cb) {
                forks[f2].release(cb);
            },
            function (cb) {
                philosophers[id].startNaive(count - 1);
            }
        ]);
    }, this.eatTime);
};

Philosopher.prototype.startNaive = function (count) {

    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    if (startTimesNaive[id] === undefined) startTimesNaive[id] = new Date().getTime();

    if (count !== 0) {
        forks[f1].acquire(function () {
            forks[f2].acquire(function () {
                philosophers[id].eatNaive(count);
            }, null)
        }, function () {
            philosophers[id].startNaive(count);
        });
    } else {
        console.log(id,",", new Date().getTime() - startTimesNaive[id]);
    }
};


Philosopher.prototype.startAsym = function (count) {
    if(this.id%2===0) {
        var tmp = this.f1;
        this.f1 = this.f2;
        this.f2 = tmp;
    }
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    if (startTimesNaive[id] === undefined) startTimesNaive[id] = new Date().getTime();

    if (count !== 0) {
        forks[f1].acquire(function () {
            forks[f2].acquire(function () {
                philosophers[id].eatAsym(count);
            })
        });
    } else {
        console.log(id,",", new Date().getTime() - startTimesNaive[id]);
    }
};

Philosopher.prototype.eatAsym = function (count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    //console.log(id + " is eating");
    setTimeout(function () {
        async.waterfall([
            function (cb) {
                forks[f1].release(cb);
            },
            function (cb) {
                forks[f2].release(cb);
            },
            function (cb) {
                philosophers[id].startAsym(count - 1);
            }
        ]);
    }, this.eatTime);
};


var Conductor = function (){
    this.counter = 4;
    return this;
};


Philosopher.prototype.giveForks = function (count) {
    var philosopher = this;
    var r = Math.floor(Math.random() * this.eatTime);
    startTimesConductor[philosopher.id] += r;
    setTimeout(function () {
        conductor.release(philosopher, count);
    }, r);
};


var Conductor = function () {
    this.counter = 4;
    this.maxTryNumber = 5;
    return this;
};

Conductor.prototype.ask = function(cbs) {
    // zaimplementuj funkcje acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwsza proba podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy proba jest nieudana, zwieksza czas oczekiwania dwukrotnie
    //    i ponawia probe itd.
    var tryNumber = 0;
    var getTable = function (waitTime, conductor) {
        setTimeout(function () {
            if (conductor.counter === 0){
                //console.log("i didn't get it");
                tryNumber++;
                getTable(Math.pow(2, tryNumber), conductor);
            }else {
                //console.log("i got it");
                conductor.counter--;
                if(cbs) cbs()
            }
        }, waitTime);
    };
    getTable(1, this);
};

Conductor.prototype.release = function(cb) {
    this.counter++;
    if(cb) cb();
};

Philosopher.prototype.eatConductor = function (count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    setTimeout(function () {
        async.waterfall([
            function (cb) {
                forks[f1].release(cb);
            },
            function (cb) {
                forks[f2].release(cb);
            },
            function (cb) {
                conductor.release(cb);
            },
            function (cb) {
                //console.log(id + " is releasing everything");
                philosophers[id].startConductor(count - 1);
            }
        ]);
    }, this.eatTime);
};

Philosopher.prototype.startConductor = function (count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    if (startTimesNaive[id] === undefined) startTimesNaive[id] = new Date().getTime();

    if (count !== 0) {
        conductor.ask(function () {
            forks[f1].acquire(function () {
                forks[f2].acquire(function () {
                    //console.log(id + " is eating");
                    philosophers[id].eatConductor(count);
                })
            });
        });
    } else {
        console.log(id,",", new Date().getTime() - startTimesNaive[id]);
    }
};


var N = 5;
var forks = [];
var philosophers = [];
var startTimesNaive = [];
var startTimesAsync = [];
var startTimesConductor = [];
var conductor = new Conductor();
for (var i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

/*for (var i = 0; i < N; i++) {
    philosophers[i].startNaive(10);
}*/


/*for (var i = 0; i < N; i++) {
    philosophers[i].startAsym(10);
}*/

for (var i = 0; i < N; i++){
    philosophers[i].startAsym(10);
}



