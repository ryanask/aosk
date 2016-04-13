'use strict';

describe('Controller Tests', function() {

    describe('RNegaraTujuan Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRNegaraTujuan;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRNegaraTujuan = jasmine.createSpy('MockRNegaraTujuan');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RNegaraTujuan': MockRNegaraTujuan
            };
            createController = function() {
                $injector.get('$controller')("RNegaraTujuanDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:rNegaraTujuanUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
