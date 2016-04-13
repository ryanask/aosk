'use strict';

describe('Controller Tests', function() {

    describe('RSektorKur Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRSektorKur;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRSektorKur = jasmine.createSpy('MockRSektorKur');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RSektorKur': MockRSektorKur
            };
            createController = function() {
                $injector.get('$controller')("RSektorKurDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:rSektorKurUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
